package com.backend.controller;

import com.backend.dto.EntityDTO;
import com.backend.dto.UnavailablePeriodDTO;
import com.backend.model.Adventure;
import com.backend.model.UnavailablePeriod;
import com.backend.service.AdventureService;
import com.backend.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/adventures")
    //@PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<List<EntityDTO>> getAllAdventuresFromInstructor(Principal instructor) {
        List<Adventure> adventures = adventureService.getAllAdventuresFromInstructor(instructor.getName());

        List<EntityDTO> dto = new ArrayList<>();
        for(Adventure a : adventures) {
            EntityDTO entityDTO = new EntityDTO(a.getId(), a.getName(), a.getDescription(), a.getAverageGrade(), a.getImages(), a.getAddress());
            dto.add(entityDTO);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/unavailablePeriod")
    //@PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<UnavailablePeriodDTO> defineUnavailablePeriodForInstructor(@RequestBody UnavailablePeriod unavailablePeriod, Principal user) throws ResponseStatusException{
        UnavailablePeriod period = instructorService.defineUnavailablePeriodForInstructor(unavailablePeriod, user.getName());
        UnavailablePeriodDTO dto = new UnavailablePeriodDTO(period.getId(), period.getFromDateTime(), period.getToDateTime(), "Instructor unavailable.");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/unavailablePeriods")
    //@PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Set<UnavailablePeriodDTO>> getAllUnavailablePeriodsForInstructor(Principal user) {
        if(user.getName() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no logged user!");

        Set<UnavailablePeriod> periods = instructorService.getAllUnavailablePeriodsForInstructor(user.getName());
        Set<UnavailablePeriodDTO> dto = getUnavailablePeriodDTOS(periods);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/unavailablePeriods/{id}")
    //@PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Set<UnavailablePeriodDTO>> getAllUnavailablePeriodsForInstructorById(@PathVariable("id") Integer id) {
        Set<UnavailablePeriod> periods = instructorService.getAllUnavailablePeriodsForInstructorById(id);

        Set<UnavailablePeriodDTO> dto = getUnavailablePeriodDTOS(periods);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private Set<UnavailablePeriodDTO> getUnavailablePeriodDTOS(Set<UnavailablePeriod> periods) {
        Set<UnavailablePeriodDTO> dto = new HashSet<>();
        for (UnavailablePeriod p : periods) {
            dto.add(new UnavailablePeriodDTO(p.getId(), p.getFromDateTime(), p.getToDateTime(), "Instructor unavailable."));
        }
        return dto;
    }
}
