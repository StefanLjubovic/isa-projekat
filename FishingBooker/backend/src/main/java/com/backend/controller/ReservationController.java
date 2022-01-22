package com.backend.controller;


import com.backend.dto.ReservationDTO;
import com.backend.dto.ReservationSaleDTO;
import com.backend.model.RentingEntity;
import com.backend.model.Reservation;
import com.backend.service.EntityService;
import com.backend.service.ReservationService;
import com.backend.service.SaleService;
import org.modelmapper.ModelMapper;
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
    private ReservationService reservationService;

    @Autowired
    private EntityService entityService;

    @Autowired
    SaleService saleService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<RentingEntity> saveReservation(@RequestBody ReservationDTO reservationDTO) {
        if(reservationDTO ==null || reservationDTO.getDurationInHours() <= 0 || reservationDTO.getDateTime() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request from client!");
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setRentingEntity(this.entityService.getEntityById(reservationDTO.getEntityId()));
        reservation.getRentingEntity().setVersion(reservationDTO.getEntityVersion());
        RentingEntity entity = reservationService.Save(reservation);
        if(entity == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given period is occupied!");
        return new ResponseEntity<>(entity,HttpStatus.OK);
    }

    @PostMapping(value = "/createByAdvertiser")
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER, SHIP_OWNER, INSTRUCTOR')")
    public ResponseEntity<ReservationDTO> saveReservationByAdvertiser(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setRentingEntity(this.entityService.getEntityById(reservationDTO.getEntityId()));
        reservation.getRentingEntity().setVersion(reservationDTO.getEntityVersion());
        ReservationDTO createdReservation = reservationService.saveReservationCreatedByAdvertiser(reservation);
        createdReservation.setEntityVersion(this.entityService.getEntityById(reservationDTO.getEntityId()).getVersion());
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
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
        Boolean isBooked = reservationService.isEntityBookedNow(id);
        return new ResponseEntity<>(isBooked, HttpStatus.OK);
    }

    @PostMapping(value = "/fast-reservation/",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<RentingEntity> saveFastReservation(@RequestBody ReservationSaleDTO dto) {
        if(dto.getSale() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request from client!");
        saleService.delete(dto.getSale());
        ReservationDTO reservationDTO= new ReservationDTO(dto.getId(),dto.getDateTime(),dto.getDurationInHours(),dto.getMaxPersons(),dto.getPrice(),dto.getCanceled(),dto.getEntityId(),dto.getEntityName(), dto.getClient());
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        reservation.setRentingEntity(this.entityService.getEntityById(reservationDTO.getEntityId()));
        RentingEntity entity = reservationService.Save(reservation);
        if(entity == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The given period is occupied!");
        return new ResponseEntity<>(entity,HttpStatus.OK);
    }

}
