package com.backend.service;

import com.backend.dto.ComplaintDTO;
import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EntityService {

    @Autowired
    IEntityRepository entityRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IComplaintRepository complaintRepository;


    public EntityService(){ }

    public List<? extends RentingEntity> GetAllEntities(int state){
        List<? extends RentingEntity> entities=null;
        if(state==0)entities=entityRepository.getEntityByClass(Adventure.class);
        else if(state==1)entities=entityRepository.getEntityByClass(Ship.class);
        else if(state==2) entities=entityRepository.getEntityByClass(Cottage.class);
        return entities;
    }

    public List<? extends RentingEntity> GetByUsersSubscriptions(String email) {
        return entityRepository.findSubscriptions(email);
    }

    public void deleteEntity(Integer id) {
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

    public Set<Sale> createSaleForEntity(Sale sale, Integer entityId) {
        RentingEntity entity = entityRepository.fetchById(entityId);
        sale.setRentingEntity(entity);
        entity.getSales().add(sale);
        entityRepository.save(entity);
        return entity.getSales();
    }
    @Transactional
    public Reservation updateUnavailablePeriod(Reservation reservation) {
        Date endDate = getEndDate(reservation.getDateTime(),reservation.getDurationInHours());
        if(!CheckOverlappingDates(reservation,endDate)){
            UnavailablePeriod reservationPeriod = new UnavailablePeriod(reservation.getDateTime(),endDate);
            try{
                RentingEntity entityToUpdate = entityRepository.fetchWithPeriods(reservation.getRentingEntity().getId());
                entityToUpdate.getUnavailablePeriods().add(reservationPeriod);
                entityRepository.save(entityToUpdate);
                reservation.setRentingEntity(entityToUpdate);
                return reservation;
            } catch (ObjectOptimisticLockingFailureException ex){
                throw new ObjectOptimisticLockingFailureException("Entity got reserved in that period try again later.",ex);
            }
        }
        return null;
    }

    private boolean CheckOverlappingDates(Reservation reservation,Date endDate) {
        for(UnavailablePeriod period : reservation.getRentingEntity().getUnavailablePeriods())
            if(reservation.getDateTime().compareTo(period.getToDateTime()) <=0 || period.getFromDateTime().compareTo(endDate) <=0)
                return true;
        return false;
    }

    private Date getEndDate(Date fromDate,Integer durationInHours) {
        Integer hoursToDays= (int) Math.ceil(Double.valueOf(durationInHours/24));
        Calendar c = Calendar.getInstance();
        c.setTime(fromDate);
        c.add(Calendar.DATE, hoursToDays);
        Date endDate= c.getTime();
        return endDate;
    }


    @Transactional(readOnly = false)
    public Reservation updateUnavailablePeriodTest(Reservation reservation) {
        Date endDate = getEndDate(reservation.getDateTime(),reservation.getDurationInHours());
        if(!false){
            UnavailablePeriod reservationPeriod = new UnavailablePeriod(reservation.getDateTime(),endDate);
            try{
                RentingEntity entityToUpdate = entityRepository.fetchWithPeriods(reservation.getRentingEntity().getId());
                entityToUpdate.getUnavailablePeriods().add(reservationPeriod);
                entityToUpdate.setVersion(reservation.getRentingEntity().getVersion());
                entityRepository.save(entityToUpdate);
                reservation.setRentingEntity(entityToUpdate);
                return reservation;
            } catch (ObjectOptimisticLockingFailureException ex){
                throw new ObjectOptimisticLockingFailureException("Entity got reserved in that period try again later.",ex);
            }
        }
        return null;
    }

    public boolean checkIfSubscribed(String email,Integer entityId){
        RentingEntity e = entityRepository.checkIfSubscribed(email,entityId);
        if(e == null) return false;
        return true;
    }

    public void createComplaint(ComplaintDTO dto, String email) {
        Complaint complaint = new Complaint(dto.getContent(),new Client(userRepository.findByEmail(email)),entityRepository.findById(dto.getEntityId()).get());
        complaintRepository.save(complaint);
    }

    public List<? extends RentingEntity> getEntitiesOnSale(int state){
        List<? extends RentingEntity> entities=null;
        if(state==0)entities=entityRepository.getEntitiesOnSale(Adventure.class);
        else if(state==1)entities=entityRepository.getEntitiesOnSale(Ship.class);
        else if(state==2) entities=entityRepository.getEntitiesOnSale(Cottage.class);
        return entities;
    }
}
