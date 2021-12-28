package com.backend.service;

import com.backend.model.Reservation;
import com.backend.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReservationService {

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired
    EntityService entityService;

    @Autowired
    MailService mailService;

    public Boolean Save(Reservation reservation){
        Reservation updatedReservation=entityService.updateUnavailablePeriod(reservation);
        if(updatedReservation==null)
            return false;
        Reservation res=reservationRepository.save(updatedReservation);
        if(res == null) return false;
        mailService.sendReservationMail(res);
        return true;
    }
}
