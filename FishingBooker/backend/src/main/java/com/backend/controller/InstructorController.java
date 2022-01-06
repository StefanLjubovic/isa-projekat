package com.backend.controller;

import com.backend.dto.EntityDTO;
import com.backend.dto.ReservationDTO;
import com.backend.dto.ReservationIncomeDTO;
import com.backend.dto.UnavailablePeriodDTO;
import com.backend.model.Adventure;
import com.backend.model.Reservation;
import com.backend.model.UnavailablePeriod;
import com.backend.service.AdventureService;
import com.backend.service.Base64ToImage;
import com.backend.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/instructor", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    AdventureService adventureService;

    private Base64ToImage base64ToImage = new Base64ToImage();

    @GetMapping("/adventures")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<List<EntityDTO>> getAllAdventuresFromInstructor(Principal instructor) throws IOException {
        List<Adventure> adventures = adventureService.getAllAdventuresFromInstructor(instructor.getName());

        List<EntityDTO> dto = new ArrayList<>();
        for(Adventure a : adventures) {
            String[] images = a.getImages().toArray(new String[a.getImages().size()]);

            EntityDTO entityDTO;
            if(images.length > 0) {
                entityDTO = new EntityDTO(a.getId(), a.getName(), a.getDescription(), a.getAverageGrade(), base64ToImage.encodeImageToBase64(images[0]), a.getAddress());
            } else {
                entityDTO = new EntityDTO(a.getId(), a.getName(), a.getDescription(), a.getAverageGrade(), base64ToImage.encodeImageToBase64("/images/undefined/no_image.jpg"), a.getAddress());
            }

            dto.add(entityDTO);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/unavailablePeriod")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<UnavailablePeriodDTO> defineUnavailablePeriodForInstructor(@RequestBody UnavailablePeriod unavailablePeriod, Principal user) throws ResponseStatusException{
        UnavailablePeriod period = instructorService.defineUnavailablePeriodForInstructor(unavailablePeriod, user.getName());
        UnavailablePeriodDTO dto = new UnavailablePeriodDTO(period.getId(), period.getFromDateTime(), period.getToDateTime(), "Instructor unavailable.");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/unavailablePeriods")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Set<UnavailablePeriodDTO>> getAllUnavailablePeriodsForInstructor(Principal user) {
        if(user.getName() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no logged user!");

        Set<UnavailablePeriod> periods = instructorService.getAllUnavailablePeriodsForInstructor(user.getName());
        Set<UnavailablePeriodDTO> dto = getUnavailablePeriodDTOS(periods);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/unavailablePeriods/{id}")
    public ResponseEntity<Set<UnavailablePeriodDTO>> getAllUnavailablePeriodsForInstructorById(@PathVariable("id") Integer id) {
        Set<UnavailablePeriod> periods = instructorService.getAllUnavailablePeriodsForInstructorById(id);

        Set<UnavailablePeriodDTO> dto = getUnavailablePeriodDTOS(periods);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @GetMapping(value = "/reservations",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Set<ReservationDTO>> getReservationsForInstructor(Principal principal) {
        List<Reservation> reservations = instructorService.getReservationsForInstructor(principal.getName());

        Set<ReservationDTO> reservationDTOS = new HashSet<>();
        for(Reservation r : reservations) {
            ReservationDTO dto = new ReservationDTO(r.getId(), r.getDateTime(), r.getDurationInHours(), r.getMaxPersons(), r.getPrice(), r.getCanceled(), r.getRentingEntity().getId(), r.getRentingEntity().getName(), r.getClient().getFirstName() + " " + r.getClient().getLastName(), r.getClient().getEmail());
            reservationDTOS.add(dto);
        }

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/reservation-income", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<List<ReservationIncomeDTO>> getReservationIncomeForCottageOwner(Principal user){
        List<ReservationIncomeDTO> reservations = this.instructorService.calculateReservationIncomeForInstructor(user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    private Set<UnavailablePeriodDTO> getUnavailablePeriodDTOS(Set<UnavailablePeriod> periods) {
        Set<UnavailablePeriodDTO> dto = new HashSet<>();
        for (UnavailablePeriod p : periods) {
            dto.add(new UnavailablePeriodDTO(p.getId(), p.getFromDateTime(), p.getToDateTime(), "Instructor unavailable."));
        }
        return dto;
    }
}
