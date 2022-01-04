package com.backend.controller;

import com.backend.dto.RevisionDTO;
import com.backend.model.RegisteredUser;
import com.backend.model.Revision;
import com.backend.service.AdvertiserService;
import com.backend.service.EntityService;
import com.backend.service.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/revision", produces = MediaType.APPLICATION_JSON_VALUE)
public class RevisionController {

    @Autowired
    private RevisionService revisionService;

    @Autowired
    private AdvertiserService advertiserService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<RevisionDTO>> getAllPendingRevisions() {
        List<Revision> revisions = revisionService.getAllPendingRevisions();

        Set<RevisionDTO> DTOs = new HashSet<>();
        for(Revision r : revisions) {
            RegisteredUser advertiser = advertiserService.findAdvertiserByEntityId(r.getReservation().getRentingEntity().getId());
            RevisionDTO dto = new RevisionDTO(r.getId(), r.getContent(), r.getApproved(), r.getMark(), r.getId(), r.getReservation().getClient().getEmail(), r.getReservation().getClient().getFirstName() + " " + r.getReservation().getClient().getLastName(), advertiser.getFirstName() + " " + advertiser.getLastName(), r.getReservation().getRentingEntity().getName(), advertiser.getRole().getName());
            DTOs.add(dto);
        }
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }

    @PostMapping(value = "save-revision")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> saveClientRevision(@RequestBody RevisionDTO dto){
        if(dto==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No registered user!");
        revisionService.saveClientRevision(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveRevision(@RequestBody Integer id) {
        revisionService.approveRevision(id);
        return new ResponseEntity<>("Revision approved!", HttpStatus.OK);
    }

    @DeleteMapping("/disapprove/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> disapproveRevision(@PathVariable("id") Integer id) {
        if (revisionService.getById(id) == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such revision!");
        revisionService.deleteById(id);
        return new ResponseEntity<>("Revision disapproved and deleted!", HttpStatus.OK);
    }
}
