package com.backend.service;

import com.backend.dto.ReservationHistoryDTO;
import com.backend.model.Client;
import com.backend.model.Cottage;
import com.backend.model.Reservation;
import com.backend.repository.ICottageRepository;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CottageOwnerService {

    @Autowired
    private ICottageRepository cottageRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    public CottageOwnerService() {}

    public List<Cottage> getAllCottagesFromCottageOwner(String email) { return cottageRepository.getCottagesByCottageOwner_Email(email); }

    public List<ReservationHistoryDTO> getReservationHistoryForCottageOwner(String email) {
        List<ReservationHistoryDTO> reservations = new ArrayList<ReservationHistoryDTO>();
        List<Cottage> cottages = getAllCottagesFromCottageOwner(email);
        for (Cottage cottage : cottages) {
            List<ReservationHistoryDTO> reservationsPerCottage = this.reservationRepository.fetchReservationHistoryByEntityName(cottage.getName());
            for (ReservationHistoryDTO reservation : reservationsPerCottage) {
                reservation.setClient(new Client(this.userRepository.findByEmail(reservation.getClientEmail())));
                reservations.add(reservation);
            }
        }
        return reservations;
    }
}
