package com.backend.service;

import com.backend.dto.ReservationHistoryDTO;
import com.backend.dto.ReservationIncomeDTO;
import com.backend.model.Client;
import com.backend.model.Ship;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IShipRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ShipOwnerService {

    @Autowired
    IShipRepository shipRepository;

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    SystemPropertyService systemPropertyService;

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
                if(this.getReservationEndTime(reservation).before(new Date())) {
                    reservation.setClient(new Client(this.userRepository.findByEmail(reservation.getClientEmail())));
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }

    public List<ReservationIncomeDTO> calculateReservationIncomeForShips(String email) {
        List<ReservationIncomeDTO> totalIncome = new ArrayList<>();
        List<ReservationHistoryDTO> allReservations = getReservationHistoryForShipOwner(email);
        Double percentage = Double.parseDouble(this.systemPropertyService.getPercentage().getValue());
        for(ReservationHistoryDTO reservation: allReservations)
                totalIncome.add(new ReservationIncomeDTO(
                        reservation.getEntityName(),
                        reservation.getPrice() * (100 - percentage)/100,
                        reservation.getDateTime(),
                        getReservationEndTime(reservation)));
        return totalIncome;
    }

    private Date getReservationEndTime(ReservationHistoryDTO dto) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dto.getDateTime());
        cal.add(Calendar.HOUR_OF_DAY, dto.getDurationInHours());
        return cal.getTime();
    }
}
