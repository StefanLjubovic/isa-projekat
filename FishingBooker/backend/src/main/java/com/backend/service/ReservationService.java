package com.backend.service;

import com.backend.dto.ReservationDTO;
import com.backend.model.*;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public Boolean Save(Reservation reservation){
        try {
            reservation.setRentingEntity(this.entityRepository.findLockedById(reservation.getRentingEntity().getId()));
        } catch(PessimisticLockingFailureException ex) { throw  new PessimisticLockingFailureException("Owner already reserved this entity!"); }
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
    public ReservationDTO saveReservationCreatedByAdvertiser(Reservation newReservation, Integer entityId) throws PessimisticLockingFailureException{
        try {
            RentingEntity entity = this.entityService.findLockedById(entityId);
        } catch(PessimisticLockingFailureException ex) { throw  new PessimisticLockingFailureException("Client already reserved this entity!"); }
        newReservation.setRentingEntity(this.entityService.findLockedById(entityId));
        List<Reservation> entityReservations = this.reservationRepository.fetchByEntityId(newReservation.getRentingEntity().getId());
        Reservation currentReservation = null;
        for(Reservation r : entityReservations) {
            if (isEntityBooked(r)) {
                currentReservation = r;
                break;
            }
        }

        try{
            if (currentReservation.equals(null))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only create reservation if there is a current reservation!");
        } catch (NullPointerException e) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can only create reservation if there is a current reservation!"); }

        newReservation.setClient(currentReservation.getClient());
        Reservation savedReservation = saveReservationIfThereIsNoOverlapping(newReservation);
        return new ReservationDTO(savedReservation.getDateTime(), savedReservation.getDurationInHours(), savedReservation.getMaxPersons(), savedReservation.getPrice(), currentReservation.getCanceled(), entityId, savedReservation.getRentingEntity().getName());
    }

    @Transactional
    public Reservation saveReservationIfThereIsNoOverlapping(Reservation newReservation) {
        if(newReservation.overlapsWithExistingUnavailablePeriods(this.entityRepository.fetchWithPeriods(newReservation.getRentingEntity().getId()).getUnavailablePeriods()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined unavailable period in this time range!");

        if (newReservation.overlapsWithExistingReservations(this.reservationRepository.fetchByEntityName(newReservation.getRentingEntity().getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined reservation in this time range!");

        if(newReservation.overlapsWithExistingSales(this.entityRepository.fetchWithSales(newReservation.getRentingEntity().getId()).getSales()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is already defined sale in this time range!");

        return saveReservationAdv(newReservation);
    }

    @Transactional
    public Reservation saveReservationAdv(Reservation newReservation) {
        try {
            Reservation savedReservation = this.reservationRepository.save(newReservation);
            return savedReservation;
        } catch (PessimisticLockingFailureException e) {
            throw new PessimisticLockingFailureException("Two or more access to database at the same time!", e);
        }
    }

    public boolean isEntityBooked(Reservation r) {
        return r.getDateTime().before(new Date()) && r.getReservationEndTime().after(new Date()) && !r.getCanceled();
    }
}
