package com.backend.controller;

import com.backend.dto.ReservationIncomeDTO;
import com.backend.model.Reservation;
import com.backend.model.SystemProperty;
import com.backend.service.ReservationService;
import com.backend.service.SystemPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.OptimisticLockException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/adminAnalytics", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminAnalyticsController {

    @Autowired
    private SystemPropertyService systemPropertyService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/percentage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemProperty> getIncomePercentage() {
        SystemProperty percentage = this.systemPropertyService.getPercentage();
        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }

    @PutMapping("/percentage/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateIncomePercentage(@RequestBody SystemProperty percentage) {
        try {
            this.systemPropertyService.updatePercentage(percentage);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Another administrator is updating income percentage now! Try again in a few minutes.");
        }

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<ReservationIncomeDTO>> getAllReservationIncomes() {
        List<Reservation> reservations = reservationService.getAllFinishedReservations();
        Double percentage = Double.parseDouble(systemPropertyService.getPercentage().getValue());

        Set<ReservationIncomeDTO> incomes = new HashSet<>();
        for(Reservation r : reservations) {
            ReservationIncomeDTO dto = new ReservationIncomeDTO(r.getRentingEntity().getName(), r.getClient().getEmail(), r.getDateTime(), r.getReservationEndTime());
            dto.setIncome(r.getPrice(), percentage);
            incomes.add(dto);
        }
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }
}
