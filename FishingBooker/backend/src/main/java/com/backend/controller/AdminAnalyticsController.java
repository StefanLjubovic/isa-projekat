package com.backend.controller;

import com.backend.service.SystemPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/adminAnalytics", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminAnalyticsController {

    @Autowired
    private SystemPropertyService systemPropertyService;

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
}
