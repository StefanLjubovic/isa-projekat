package com.backend.service;

import com.backend.dto.ReservationDTO;
import com.backend.model.*;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        reservation.setRentingEntity(entityService.fetchWithUnavailablePeriods(reservation.getRentingEntity().getId()));
        Reservation updatedReservation=entityService.checkIfAlreadyReserved(reservation);
        if(updatedReservation==null)
            return false;
        saveTransactional(updatedReservation);
        mailService.sendReservationMail(updatedReservation);
        return true;
    }

    @Transactional
    public void saveTransactional(Reservation reservation){
        try{
            entityRepository.save(reservation.getRentingEntity());
            reservationRepository.save(reservation);
        }catch (ObjectOptimisticLockingFailureException e){
            throw new ObjectOptimisticLockingFailureException("Entity is already reserved!",e);
        }
    }

    @Transactional
    public List<ReservationDTO> getClientFutureReservations(String email){
        return reservationRepository.fetchByClientEmail(email,new Date());
    }

    public List<ReservationDTO> cancelReservation(Integer id,String email) {
        Reservation reservation = reservationRepository.fetchWithUnavailablePeriods(id);
        reservation.setCanceled(true);
        reservationRepository.save(reservation);
        return getClientFutureReservations(email);
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
}
