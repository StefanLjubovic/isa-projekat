package com.backend.service;

import com.backend.dto.ReservationHistoryDTO;
import com.backend.model.Client;
import com.backend.model.Ship;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IShipRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipOwnerService {

    @Autowired
    IShipRepository shipRepository;

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired
    IUserRepository userRepository;

    public ShipOwnerService() {}

    public List<Ship> getAllShipsFromShipOwner(String email) {
        return shipRepository.getShipsByShipOwner_Email(email);
    }

    public List<ReservationHistoryDTO> getReservationHistoryForShipOwner(String email) {
        List<ReservationHistoryDTO> reservations = new ArrayList<ReservationHistoryDTO>();
        List<Ship> ships = getAllShipsFromShipOwner(email);
        for (Ship ship: ships) {
            List<ReservationHistoryDTO> reservationsPerShip = this.reservationRepository.fetchReservationHistoryByEntityName(ship.getName());
            for (ReservationHistoryDTO reservation : reservationsPerShip) {
                reservation.setClient(new Client(this.userRepository.findByEmail(reservation.getClientEmail())));
                reservations.add(reservation);
            }
        }
        return reservations;
    }
}
