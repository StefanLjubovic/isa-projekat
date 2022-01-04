package com.backend.controller;


import com.backend.dto.ReservationDTO;
import com.backend.dto.ReservationSaleDTO;
import com.backend.dto.RevisionDTO;
import com.backend.model.Reservation;
import com.backend.model.Sale;
import com.backend.service.ReservationService;
import com.backend.service.RevisionService;
import com.backend.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/reservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    RevisionService revisionService;

    @Autowired
    SaleService saleService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> saveReservation(@RequestBody Reservation reservation) {
        if(reservation ==null || reservation.getDurationInHours() <= 0 || reservation.getDateTime() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request from client!");
        boolean saved = reservationService.Save(reservation);
        if(!saved) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given period is occupied!");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/future-reservations",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> getClientFutureReservations(Principal principal){
        if(principal == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No registered user!");
        List<ReservationDTO> r= reservationService.getClientFutureReservations(principal.getName());
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @PutMapping(value = "/cancel-reservation/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> cancelReservation(@PathVariable Integer id,Principal principal){
        if(id ==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation was not selected!");
        return new ResponseEntity<>( reservationService.cancelReservation(id,principal.getName()),HttpStatus.OK);
    }

    @GetMapping(value = "/history-reservations/{classType}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ReservationDTO>> getClientHistoryOfReservations(Principal principal,@PathVariable String classType){
        if(principal == null || classType==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No registered user!");
        List<ReservationDTO> r= reservationService.getHistoryOfReservations(principal.getName(),classType);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @PostMapping(value = "save-revision",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> saveClientRevision(@RequestBody RevisionDTO dto){
        if(dto==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No registered user!");
        revisionService.saveClientRevision(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/entity/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ReservationDTO>> getReservationsByEntityId(@PathVariable("id") Integer id) {
        List<Reservation> reservations = reservationService.getReservationsByEntityId(id);

        Set<ReservationDTO> reservationDTOS = new HashSet<>();
        for(Reservation r : reservations) {
            ReservationDTO dto = new ReservationDTO(r.getId(), r.getDateTime(), r.getDurationInHours(), r.getMaxPersons(), r.getPrice(), r.getCanceled(), r.getRentingEntity().getId(), r.getRentingEntity().getName());
            reservationDTOS.add(dto);
        }
        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/booked/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<Boolean> isEntityBooked(@PathVariable("id") Integer id) {
        Boolean isBooked = reservationService.isEntityBooked(id);
        return new ResponseEntity<>(isBooked, HttpStatus.OK);
    }
    @PostMapping(value = "/fast-reservation/",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Boolean> saveFastReservation(@RequestBody ReservationSaleDTO dto) {
        if(dto.getReservation() ==null || dto.getSale() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request from client!");
        saleService.delete(dto.getSale());
        boolean saved = reservationService.Save(dto.getReservation());
        if(!saved) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given period is occupied!");
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

}
