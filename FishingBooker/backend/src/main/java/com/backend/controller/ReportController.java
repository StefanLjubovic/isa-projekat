package com.backend.controller;

import com.backend.dto.ReportDTO;
import com.backend.model.Client;
import com.backend.model.RegisteredUser;
import com.backend.model.Report;
import com.backend.service.AdvertiserService;
import com.backend.service.EntityService;
import com.backend.service.ReportService;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdvertiserService advertiserService;

    public ReportController() {}

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<String> addNewReport(Principal user, @RequestBody ReportDTO reportDTO)  {
        Report report = new Report(reportDTO.getContent(), reportDTO.isBadReview(), reportDTO.isNotAppeared(),
                        new Client(this.userService.findByEmail(reportDTO.getClientEmail())),
                        this.entityService.getEntityById(reportDTO.getRentingEntityId()));
        this.reportService.save(report);
        return new ResponseEntity<>("Report sent to administrator!", HttpStatus.CREATED);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<ReportDTO>> getAllReports() {
        List<Report> reports = reportService.getAllReports();

        Set<ReportDTO> DTOs = new HashSet<>();
        for(Report r : reports) {
            RegisteredUser advertiser = advertiserService.findAdvertiserByEntityId(r.getRentingEntity().getId());
            ReportDTO dto = new ReportDTO(r.getContent(), r.isBadReview(), r.isNotAppeared(), r.getClient().getEmail(), r.getRentingEntity().getId(), r.getClient().getFullName(), advertiser.getFullName(), r.getRentingEntity().getName());
            DTOs.add(dto);
        }

        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }
}
