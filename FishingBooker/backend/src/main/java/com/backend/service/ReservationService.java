package com.backend.service;

import com.backend.dto.ReservationDTO;
import com.backend.model.*;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        if(!saveTransactional(updatedReservation)) return false;
        mailService.sendReservationMail(updatedReservation);
        return true;
    }

    @Transactional(readOnly = false)
    public boolean saveTransactional(Reservation reservation){
        try{
            entityRepository.save(reservation.getRentingEntity());
            reservationRepository.save(reservation);
        }catch (ObjectOptimisticLockingFailureException e){
            return false;
        }catch(PessimisticLockingFailureException ex){
            return false;
        }
        return true;
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

    public Boolean isEntityBookedNow(Integer id) {
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

    @Transactional
    public void saveTransactionalForTest(Reservation reservation){
        try{
            entityRepository.save(reservation.getRentingEntity());
            reservationRepository.save(reservation);
        }catch (ObjectOptimisticLockingFailureException e){
            throw new ObjectOptimisticLockingFailureException("Entity is already reserved!",e);
        }
    }

    @Transactional
    public String saveReservationCreatedByAdvertiser(Reservation newReservation) {
        List<Reservation> entityReservations = this.reservationRepository.fetchByEntityId(newReservation.getRentingEntity().getId());
        Reservation currentReservation = null;
        for(Reservation r : entityReservations) {
            if (isEntityBookedNow(r)) {
                currentReservation = r;
                break;
            }
        }

        try{
            if (currentReservation.equals(null))
                return  "You can only create reservation if there is a current reservation!";
        } catch (NullPointerException e) { return  "You can only create reservation for client with current reservation!"; }


        newReservation.setClient(currentReservation.getClient());
        return saveReservationIfThereIsNoOverlapping(newReservation);
    }

    @Transactional
    private String saveReservationIfThereIsNoOverlapping(Reservation newReservation) {
        if(newReservation.overlapsWithExistingUnavailablePeriods(this.entityRepository.fetchWithPeriods(newReservation.getRentingEntity().getId()).getUnavailablePeriods()))
            return "There is already defined unavailable period in this time range!";

        if (newReservation.overlapsWithExistingReservations(this.reservationRepository.fetchByEntityName(newReservation.getRentingEntity().getName())))
            return "There is already defined reservation in this time range!";

        if(newReservation.overlapsWithExistingSales(this.entityRepository.fetchWithSales(newReservation.getRentingEntity().getId()).getSales()))
            return "There is already defined sale in this time range!";

        saveReservationAdv(newReservation);

        return "Successfully created reservation!";
    }

    @Transactional
    public void saveReservationAdv(Reservation newReservation) {
        try {
            this.reservationRepository.save(newReservation);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ObjectOptimisticLockingFailureException("Entity is already reserved!", e);
        }
    }

    @Transactional
    public boolean isEntityBooked(Reservation r) {
        return r.getDateTime().before(new Date()) && r.getReservationEndTime().after(new Date()) && !r.getCanceled();
    }
}
