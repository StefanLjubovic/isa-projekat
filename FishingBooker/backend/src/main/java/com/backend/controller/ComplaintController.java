package com.backend.controller;

import com.backend.dto.ComplaintDTO;
import com.backend.model.Complaint;
import com.backend.model.RegisteredUser;
import com.backend.service.AdvertiserService;
import com.backend.service.ComplaintService;
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
@RequestMapping(value = "/complaint", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    @Autowired
    AdvertiserService advertiserService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<ComplaintDTO>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();

        Set<ComplaintDTO> DTOs = new HashSet<>();
        for (Complaint c : complaints) {
            RegisteredUser advertiser = advertiserService.findAdvertiserByEntityId(c.getRentingEntity().getId());
            ComplaintDTO dto = new ComplaintDTO(c.getId(), c.getContent(), c.getRentingEntity().getId(), c.getClient().getEmail(), c.getClient().getFullName(), advertiser.getFullName(), c.getRentingEntity().getName(), advertiser.getRole().getName());
            DTOs.add(dto);
        }
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }

    @PostMapping("/save-complaint")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> createClientComplaint(@RequestBody ComplaintDTO dto, Principal principal){
        complaintService.createComplaint(dto,principal.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/respond/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> respondToComplaint(@PathVariable("id") Integer id, @RequestBody String response) {
        complaintService.respondToComplaint(id, response);
        return new ResponseEntity<>("Responded to complaint!", HttpStatus.OK);
    }
}
