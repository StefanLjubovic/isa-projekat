package com.backend.service;

import com.backend.dto.ReservationDTO;
import com.backend.model.*;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;


@Service
public class ReservationService {

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired
    EntityService entityService;

    @Autowired
    MailService mailService;

    @Autowired
    IEntityRepository entityRepository;

    public Boolean Save(Reservation reservation){
        Reservation updatedReservation=entityService.updateUnavailablePeriod(reservation);
        if(updatedReservation==null)
            return false;
        Reservation res=reservationRepository.save(updatedReservation);
        if(res == null) return false;
        mailService.sendReservationMail(res);
        return true;
    }

    @Transactional
    public List<ReservationDTO> getClientFutureReservations(String email){
        return reservationRepository.fetchByClientEmail(email,new Date());
    }

    public List<ReservationDTO> cancelReservation(Integer id,String email) {
        Reservation reservation = reservationRepository.fetchWithUnavailablePeriods(id);
        reservation.setCanceled(true);
        UnavailablePeriod removedPeriod = null;
        for(UnavailablePeriod period : reservation.getRentingEntity().getUnavailablePeriods())
            if(period.getFromDateTime().equals(reservation.getDateTime()))
                removedPeriod=period;
        if(removedPeriod != null) {
            RentingEntity entityToUpdate = updateUnavailablePeriodForEntity(reservation, removedPeriod);
            reservation.getRentingEntity().setUnavailablePeriods(entityToUpdate.getUnavailablePeriods());
        }
        reservationRepository.save(reservation);
        return getClientFutureReservations(email);
    }

    private RentingEntity updateUnavailablePeriodForEntity(Reservation reservation, UnavailablePeriod removedPeriod) {
        RentingEntity entityToUpdate = entityRepository.fetchWithPeriods(reservation.getRentingEntity().getId());
        Integer periodId = removedPeriod.getId();
        entityToUpdate.setUnavailablePeriods(
                entityToUpdate.getUnavailablePeriods().stream().filter(p -> {
                    return p.getId() != periodId;
                }).collect(Collectors.toSet())
        );
        entityRepository.save(entityToUpdate);
        return entityToUpdate;
    }


    public List<ReservationDTO> getHistoryOfReservations(String email,String classType){
        if(classType.equals("Adventure")) {
            List<ReservationDTO> r =reservationRepository.fetchHistoryByClientEmail(email,new Date(), Adventure.class);
            return reservationRepository.fetchHistoryByClientEmail(email,new Date(), Adventure.class);}
        else if(classType.equals("Cottage")) return reservationRepository.fetchHistoryByClientEmail(email,new Date(), Cottage.class);
        return reservationRepository.fetchHistoryByClientEmail(email,new Date(), Ship.class);
    }

    public List<Reservation> getReservationsByEntityId(Integer id) {
        return reservationRepository.getReservationByRentingEntity_Id(id).stream().filter(r -> r.getCanceled() == Boolean.FALSE).collect(Collectors.toList());
    }

    public Boolean isEntityBooked(Integer id) {
        List<Reservation> reservations = reservationRepository.getReservationByRentingEntity_Id(id);
        for(Reservation r : reservations) {
            if(r.getDateTime().before(new Date()) && r.getReservationEndTime().after(new Date()) && !r.getCanceled()) return true;
        }
        return false;
    }

    public List<Reservation> getAllFinishedReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<Reservation> finishedReservations = new ArrayList<>();

        for(Reservation r : reservations) {
            if(r.getReservationEndTime().before(new Date())) {
                finishedReservations.add(r);
            }
        }

        return finishedReservations;
    }
}
