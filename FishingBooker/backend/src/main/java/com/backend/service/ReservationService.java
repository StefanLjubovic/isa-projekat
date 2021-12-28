package com.backend.service;

import com.backend.dto.ReservationDTO;
import com.backend.model.RentingEntity;
import com.backend.model.Reservation;
import com.backend.model.UnavailablePeriod;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
