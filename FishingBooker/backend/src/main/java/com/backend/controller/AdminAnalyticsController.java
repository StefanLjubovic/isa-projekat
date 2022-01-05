package com.backend.controller;

import com.backend.dto.EntityIncomeDTO;
import com.backend.model.Reservation;
import com.backend.service.ReservationService;
import com.backend.service.SystemPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Double> getIncomePercentage() {
        Double percentage = this.systemPropertyService.getPercentage();
        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }

    @PutMapping("/percentage/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateIncomePercentage(@RequestBody Double percentage) {
        this.systemPropertyService.updatePercentage(percentage);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/income")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<EntityIncomeDTO>> getAllEntityIncomes() {
        List<Reservation> reservations = reservationService.getAllFinishedReservations();
        Double percentage = systemPropertyService.getPercentage();

        Set<EntityIncomeDTO> incomes = new HashSet<>();
        for(Reservation r : reservations) {
            EntityIncomeDTO dto = new EntityIncomeDTO(r.getRentingEntity().getName(), r.getClient().getEmail(), r.getDateTime(), r.getReservationEndTime());
            dto.setIncome(r.getPrice(), percentage);
            incomes.add(dto);
        }
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }
}
