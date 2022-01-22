package com.backend.service;

import com.backend.dto.ReservationHistoryDTO;
import com.backend.dto.ReservationIncomeDTO;
import com.backend.model.*;
import com.backend.repository.IAdventureRepository;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IUnavailablePeriodRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUnavailablePeriodRepository unavailablePeriodRepository;

    @Autowired
    private IAdventureRepository adventureRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private SystemPropertyService systemPropertyService;

    @Transactional(readOnly = false)
    public UnavailablePeriod defineUnavailablePeriodForInstructor(UnavailablePeriod unavailablePeriod, String instructorEmail) {
        FishingInstructor fishingInstructor = userRepository.fetchByEmail(instructorEmail);
        List<Adventure> adventures = adventureRepository.fetchAdventuresByFishingInstructor_Email(instructorEmail);

        if (overlapsWithExistingUnavailablePeriod(unavailablePeriod, fishingInstructor))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined unavailable period in this time range!");
        if (overlapsWithExistingSale(unavailablePeriod, adventures))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined sale in this time range!");
        if (overlapsWithExistingReservation(unavailablePeriod, instructorEmail))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already booked reservation in this time range!");

        UnavailablePeriod savedPeriod = unavailablePeriodRepository.save(unavailablePeriod);
        fishingInstructor.getUnavailablePeriods().add(savedPeriod);
        userRepository.save(fishingInstructor);
        saveUnavailablePeriodToAdventures(adventures, savedPeriod);

        return savedPeriod;
    }

    @Transactional(readOnly = false)
    public void saveUnavailablePeriodToAdventures(List<Adventure> adventures, UnavailablePeriod savedPeriod) {
        for(Adventure a : adventures) {
            a.getUnavailablePeriods().add(savedPeriod);
            adventureRepository.save(a);
        }
    }

    @Transactional(readOnly = true)
    public Set<UnavailablePeriod> getAllUnavailablePeriodsForInstructor(String instructorEmail) {
        FishingInstructor fishingInstructor = userRepository.fetchByEmail(instructorEmail);
        return fishingInstructor.getUnavailablePeriods();
    }

    @Transactional(readOnly = true)
    public Set<UnavailablePeriod> getAllUnavailablePeriodsForInstructorById(Integer id) {
        FishingInstructor fishingInstructor = userRepository.fetchInstructorWithUnavailablePeriodsById(id);
        return fishingInstructor.getUnavailablePeriods();
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsForInstructor(String email) {
        List<Adventure> instructorAdventures = adventureRepository.getAdventureByFishingInstructorEmail(email);
        List<Reservation> reservations = new ArrayList<>();
        for(Adventure a : instructorAdventures) {
            List<Reservation> r = reservationRepository.fetchByEntityId(a.getId()).stream().filter(res -> res.getCanceled() == Boolean.FALSE).collect(Collectors.toList());
            reservations.addAll(r);
        }
        return reservations;
    }

    @Transactional(readOnly = true)
    public List<Reservation> getCanceledReservationsForInstructor(String email) {
        List<Adventure> instructorAdventures = adventureRepository.getAdventureByFishingInstructorEmail(email);
        List<Reservation> reservations = new ArrayList<>();
        for(Adventure a : instructorAdventures) {
            List<Reservation> r = reservationRepository.fetchByEntityId(a.getId()).stream().filter(res -> res.getCanceled() == Boolean.TRUE).collect(Collectors.toList());
            reservations.addAll(r);
        }
        return reservations;
    }

    private boolean overlapsWithExistingReservation(UnavailablePeriod unavailablePeriod, String instructorEmail) {
        List<Adventure> instructorAdventures = adventureRepository.getAdventureByFishingInstructorEmail(instructorEmail);

        for(Adventure adventure : instructorAdventures) {
            List<Reservation> reservations = reservationRepository.fetchByEntityId(adventure.getId());
            for(Reservation r : reservations) {
                if (r.getDateTime().before(unavailablePeriod.getToDateTime()) && r.getReservationEndTime().after(unavailablePeriod.getFromDateTime()))
                    return true;
            }
        }

        return false;
    }

    private boolean overlapsWithExistingSale(UnavailablePeriod unavailablePeriod, List<Adventure> adventures) {
        for(Adventure a : adventures) {
            for(Sale s : a.getSales()) {
                if (s.getDateTimeFrom().before(unavailablePeriod.getToDateTime()) && s.getSaleEndTime().after(unavailablePeriod.getFromDateTime()))
                    return true;
            }
        }

        return false;
    }

    private boolean overlapsWithExistingUnavailablePeriod(UnavailablePeriod unavailablePeriod, FishingInstructor fishingInstructor) {
        for (UnavailablePeriod period : fishingInstructor.getUnavailablePeriods()) {
            if(period.getFromDateTime().before(unavailablePeriod.getToDateTime()) && period.getToDateTime().after(unavailablePeriod.getFromDateTime()))
                return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<ReservationIncomeDTO> calculateReservationIncomeForInstructor(String email) {
        List<ReservationIncomeDTO> totalIncome = new ArrayList<>();
        totalIncome.addAll(getTotalIncomeFromNonCanceledReservations(email));
        totalIncome.addAll(getTotalIncomeFromCanceledReservations(email));
        return totalIncome;
    }

    private List<ReservationIncomeDTO> getTotalIncomeFromNonCanceledReservations(String email) {
        List<ReservationIncomeDTO> totalIncome = new ArrayList<>();
        List<Reservation> allReservations = getReservationsForInstructor(email);
        Double percentage = Double.parseDouble(this.systemPropertyService.getPercentage().getValue());
        for(Reservation reservation: allReservations)
            totalIncome.add(new ReservationIncomeDTO(
                    reservation.getRentingEntity().getName(),
                    reservation.getPrice() * (100 - percentage)/100,
                    reservation.getDateTime(),
                    reservation.getReservationEndTime()));
        return totalIncome;
    }

    private Collection<? extends ReservationIncomeDTO> getTotalIncomeFromCanceledReservations(String email) {
        List<ReservationIncomeDTO> totalIncome = new ArrayList<>();
        List<Reservation> allReservations = getCanceledReservationsForInstructor(email);
        for(Reservation reservation: allReservations) {
            Double percentage = reservation.getRentingEntity().getCancellationPercentage();
            totalIncome.add(new ReservationIncomeDTO(
                    reservation.getRentingEntity().getName(),
                    reservation.getPrice() * percentage / 100,
                    reservation.getDateTime(),
                    reservation.getReservationEndTime()));
        }
        return totalIncome;
    }
}
