package com.backend.service;

import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EntityService {

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IRevisionRepository revisionRepository;

    @Autowired
    private IComplaintRepository complaintRepository;

    @Autowired
    private IReportRepository reportRepository;


    public EntityService(){ }

    public List<? extends RentingEntity> GetAllEntities(int state){
        List<? extends RentingEntity> entities=null;
        if(state==0)entities=entityRepository.getEntityByClass(Adventure.class);
        else if(state==1)entities=entityRepository.getEntityByClass(Ship.class);
        else if(state==2) entities=entityRepository.getEntityByClass(Cottage.class);
        return entities;
    }
    @Cacheable("entity")
    public RentingEntity getEntityById(Integer id) {
        return entityRepository.findById(id).get();
    }

    public List<? extends RentingEntity> GetByUsersSubscriptions(String email) {
        return entityRepository.findSubscriptions(email);
    }

    @Transactional
    public RentingEntity findLockedById(Integer id){
        return entityRepository.findLockedById(id);
    }

    @Transactional
    @CacheEvict(cacheNames = "entity", key = "#id")
    public void deleteEntity(Integer id) {
        if(isEntityBooked(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity is now booked.");

        List<Client> clients = userRepository.fetchAllClients();

        for (Client c : clients) {
            for(RentingEntity e : c.getSubscriptions()) {
                if(e.getId() == id){
                    c.getSubscriptions().remove(e);
                    userRepository.save(c);
                    break;
                }
            }
        }

        entityRepository.deleteById(id);
    }

    private boolean isEntityBooked(Integer id) {
        List<Reservation> reservations = reservationRepository.getReservationByRentingEntity_Id(id);
        for(Reservation r : reservations) {
            if (r.getDateTime().before(new Date()) && r.getReservationEndTime().after(new Date()) && !r.getCanceled())
                return true;
        }
        for(Reservation r : reservations) {
            revisionRepository.deleteAllByReservation_Id(r.getId());
            complaintRepository.deleteAllByRentingEntity_Id(r.getId());
            reportRepository.deleteAllByRentingEntity_Id(r.getId());
            reservationRepository.delete(r);
        }
        return false;
    }

    public Reservation checkIfAlreadyReserved(Reservation reservation) {
        Date endDate = getEndDate(reservation.getDateTime(),reservation.getDurationInHours());
        if(!checkOverlappingDates(reservation,endDate)){
             return reservation;
        }
        return null;
    }

    private boolean checkOverlappingDates(Reservation reservation, Date endDate) {
        if (checkEntityPeriods(reservation, endDate)) return true;
        if (checkReservationPeriods(reservation, endDate)) return true;
        if(checkFishingInstructorPeriods(reservation,endDate)) return true;
        return false;
    }
    private boolean checkFishingInstructorPeriods(Reservation reservation,Date endDate){
        FishingInstructor instructor = userRepository.fetchByAdventureId(reservation.getRentingEntity().getId());
        if(instructor == null) return false;
        for (UnavailablePeriod period : instructor.getUnavailablePeriods())
            if (period.getFromDateTime().compareTo(endDate) <= 0 &&
                    period.getToDateTime().compareTo(reservation.getDateTime()) >= 0)
                return true;
        return false;
    }

    private boolean checkReservationPeriods(Reservation reservation, Date endDate) {
        for(Reservation dbReservation: reservationRepository.getReservationByRentingEntity_Id(reservation.getRentingEntity().getId())){
            Date dbEndDate =getEndDate(dbReservation.getDateTime(),dbReservation.getDurationInHours());
            if(dbReservation.getDateTime().compareTo(endDate) <=0 &&
                    dbEndDate.compareTo(reservation.getDateTime())>=0)
                return true;
        }
        return false;
    }

    private boolean checkEntityPeriods(Reservation reservation, Date endDate) {
        for(UnavailablePeriod period : reservation.getRentingEntity().getUnavailablePeriods())
            if(period.getFromDateTime().compareTo(endDate) <=0 &&
                    period.getToDateTime().compareTo(reservation.getDateTime())>=0)
                return true;
        return false;
    }

    private Date getEndDate(Date fromDate,Integer durationInHours) {
        Calendar c = Calendar.getInstance();
        c.setTime(fromDate);
        c.add(Calendar.HOUR_OF_DAY,durationInHours);
        Date endDate= c.getTime();
        return endDate;
    }

    @Transactional(readOnly = true)
    public RentingEntity fetchWithUnavailablePeriods(Integer id){
        return entityRepository.fetchWithPeriods(id);
    }

    @Transactional(readOnly = true)
    public RentingEntity fetchWithSales (Integer id) { return entityRepository.fetchWithSales(id); }

    public boolean checkIfSubscribed(String email,Integer entityId){
        RentingEntity e = entityRepository.checkIfSubscribed(email,entityId);
        if(e == null) return false;
        return true;
    }

    public List<? extends RentingEntity> getEntitiesOnSale(int state){
        List<? extends RentingEntity> entities=null;
        if(state==0)entities=entityRepository.getEntitiesOnSale(Adventure.class);
        else if(state==1)entities=entityRepository.getEntitiesOnSale(Ship.class);
        else if(state==2) entities=entityRepository.getEntitiesOnSale(Cottage.class);
        return entities;
    }

    public List<? extends RentingEntity> getAvailableEntities(UnavailablePeriod unavailablePeriod,int state){
        List<? extends RentingEntity> entities=null;
        if(state==0)entities=entityRepository.getEntityByClassWithPeriods(Adventure.class);
        else if(state==1)entities=entityRepository.getEntityByClassWithPeriods(Ship.class);
        else if(state==2) entities=entityRepository.getEntityByClassWithPeriods(Cottage.class);
        entities=entities
                .stream()
                .filter(e-> !checkOverlappingDates(e,unavailablePeriod) && instructorAvailable(e,unavailablePeriod)
                && !checkReservationPeriods(e,unavailablePeriod))
                .collect(Collectors.toList());
        return entities;
    }

    private boolean instructorAvailable(RentingEntity e, UnavailablePeriod unavailablePeriod) {
        FishingInstructor instructor = userRepository.fetchByAdventureId(e.getId());
        if(instructor == null) return true;
        for (UnavailablePeriod period : instructor.getUnavailablePeriods())
            if (period.getFromDateTime().compareTo(unavailablePeriod.getToDateTime()) <= 0 &&
                    period.getToDateTime().compareTo(unavailablePeriod.getFromDateTime()) >= 0)
                return false;
        return true;
    }
    private boolean checkReservationPeriods(RentingEntity entity,UnavailablePeriod unavailablePeriod) {
        for(Reservation dbReservation: reservationRepository.getReservationByRentingEntity_Id(entity.getId())){
            Date dbEndDate =getEndDate(dbReservation.getDateTime(),dbReservation.getDurationInHours());
            if(dbReservation.getDateTime().compareTo(unavailablePeriod.getToDateTime()) <=0 &&
                    dbEndDate.compareTo(unavailablePeriod.getFromDateTime())>=0)
                return true;
        }
        return false;
    }

    private boolean checkOverlappingDates(RentingEntity e, UnavailablePeriod unavailablePeriod) {
        for(UnavailablePeriod period : e.getUnavailablePeriods())
            if(period.getFromDateTime().compareTo(unavailablePeriod.getToDateTime()) <=0 &&
                period.getToDateTime().compareTo(unavailablePeriod.getFromDateTime())>=0)
                return true;
        return false;
    }

}
