package com.backend.service;

import com.backend.model.RegisteredUser;
import com.backend.model.RentingEntity;
import com.backend.model.Reservation;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeleteUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AdvertiserService advertiserService;

    @Autowired
    private IComplaintRepository complaintRepository;

    @Autowired
    private IReportRepository reportRepository;

    @Autowired
    private IDeleteRequestRepository deleteRequestRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IRevisionRepository revisionRepository;

    @Autowired
    private EntityService entityService;

    @Transactional
    public void deleteUser(Integer id) {
        RegisteredUser registeredUser = userRepository.findById(id).get();
        deleteRequestRepository.deleteAllByUser_Id(id);
        complaintRepository.deleteAllByClient_Id(registeredUser.getId());

        if (registeredUser.getRole().getName().equals("ROLE_COTTAGE_OWNER") ||
                registeredUser.getRole().getName().equals("ROLE_SHIP_OWNER") ||
                registeredUser.getRole().getName().equals("ROLE_INSTRUCTOR")) {
            List<? extends RentingEntity> entities = advertiserService.findEntitiesByAdvertiserId(registeredUser.getId());

            if(entities != null) {
                for(RentingEntity entity : entities ) {
                    entityService.deleteEntity(entity.getId());
                }
            }
        }

        if (registeredUser.getRole().getName().equals("ROLE_CLIENT")) {
            reportRepository.deleteAllByClient_Id(registeredUser.getId());

            List<Reservation> reservations = reservationRepository.getReservationsByClient_Id(registeredUser.getId());
            for(Reservation r : reservations) {
                revisionRepository.deleteAllByReservation_Id(r.getId());
            }
            reservationRepository.deleteAllByClient_Id(registeredUser.getId());
        }

        //this.verificationTokenService.DeleteTokenByUser(registrationRequestRepository.getById(id));
        userRepository.delete(registeredUser);
    }
}
