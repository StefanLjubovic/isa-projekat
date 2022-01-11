package com.backend.service;

import com.backend.dto.ReservationHistoryDTO;
import com.backend.dto.ReservationIncomeDTO;
import com.backend.model.Client;
import com.backend.model.Cottage;
import com.backend.model.Reservation;
import com.backend.repository.ICottageRepository;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CottageOwnerService {

    @Autowired
    private ICottageRepository cottageRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private SystemPropertyService systemPropertyService;

    public CottageOwnerService() {}

    public List<Cottage> getAllCottagesFromCottageOwner(String email) { return cottageRepository.getCottagesByCottageOwner_Email(email); }

    public List<ReservationHistoryDTO> getReservationHistoryForCottageOwner(String email) {
        List<ReservationHistoryDTO> reservations = new ArrayList<ReservationHistoryDTO>();
        List<Cottage> cottages = getAllCottagesFromCottageOwner(email);
        for (Cottage cottage : cottages) {
            List<ReservationHistoryDTO> reservationsPerCottage = this.reservationRepository.fetchReservationHistoryByEntityName(cottage.getName());
            for (ReservationHistoryDTO reservation : reservationsPerCottage) {
                if(this.getReservationEndTime(reservation).before(new Date())) {
                    reservation.setClient(new Client(this.userRepository.findByEmail(reservation.getClientEmail())));
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    public List<ReservationIncomeDTO> calculateReservationIncomeForCottages(String email){
        List<ReservationIncomeDTO> totalIncome = new ArrayList<>();
        List<ReservationHistoryDTO> allReservations = getReservationHistoryForCottageOwner(email);
        for(ReservationHistoryDTO reservation: allReservations)
                totalIncome.add(new ReservationIncomeDTO(
                        reservation.getEntityName(),
                        reservation.getPrice() * (100 - this.systemPropertyService.getPercentage())/100,
                        reservation.getDateTime(),
                        getReservationEndTime(reservation)));
        return totalIncome;
    }

    private Date getReservationEndTime(ReservationHistoryDTO dto) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dto.getDateTime());
        cal.add(Calendar.HOUR_OF_DAY, dto.getDurationInHours());
        return cal.getTime();
    }
}
