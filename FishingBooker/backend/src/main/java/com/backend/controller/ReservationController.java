package com.backend.controller;


import com.backend.dto.ReservationDTO;
import com.backend.dto.RevisionDTO;
import com.backend.model.Reservation;
import com.backend.model.Revision;
import com.backend.service.ReservationService;
import com.backend.service.RevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    @Autowired
    ReservationService service;

    @Autowired
    RevisionService revisionService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> saveReservation(@RequestBody Reservation reservation) {
        if(reservation ==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request from client!");
        boolean saved = service.Save(reservation);
        if(!saved) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given period is occupied!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/future-reservations",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> getClientFutureReservations(Principal principal){
        if(principal == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No registered user!");
        List<ReservationDTO> r=service.getClientFutureReservations(principal.getName());
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @PutMapping(value = "/cancel-reservation/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> cancelReservation(@PathVariable Integer id,Principal principal){
        if(id ==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation was not selected!");
        return new ResponseEntity<>( service.cancelReservation(id,principal.getName()),HttpStatus.OK);
    }

    @GetMapping(value = "/history-reservations/{classType}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> getClientHistoryOfReservations(Principal principal,@PathVariable String classType){
        if(principal == null || classType==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No registered user!");
        List<ReservationDTO> r=service.getHistoryOfReservations(principal.getName(),classType);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @PostMapping(value = "save-revision",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> saveClientRevision(@RequestBody RevisionDTO dto){
        if(dto==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No registered user!");
        revisionService.saveClientRevision(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
