package com.backend.controller;

import com.backend.dto.ReportDTO;
import com.backend.model.Client;
import com.backend.model.Report;
import com.backend.service.EntityService;
import com.backend.service.ReportService;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    @Autowired
    private ReportService reportService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private UserService userService;

    public ReportController() {}

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<String> addNewReport(Principal user, @RequestBody ReportDTO reportDTO) throws AccessDeniedException {
        Report report = new Report(reportDTO.getContent(), reportDTO.isBadReview(), reportDTO.isNotAppeared(),
                        new Client(this.userService.findById(reportDTO.getClientId())),
                        this.entityService.getEntityById(reportDTO.getRentingEntityId()));
        this.reportService.save(report);
        System.out.println("********************************************");
        System.out.println(reportDTO.isBadReview());
        System.out.println(reportDTO.isNotAppeared());
        System.out.println("********************************************");
        return new ResponseEntity<>("Report sent to administrator!", HttpStatus.CREATED);
    }
}
