package com.backend.controller;

import com.backend.dto.CottageDTO;
import com.backend.dto.UpdateCottageDTO;
import com.backend.model.Adventure;
import com.backend.model.Cottage;
import com.backend.model.FishingInstructor;
import com.backend.repository.IUserRepository;
import com.backend.service.AdventureService;
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

@Controller
@RequestMapping(value = "/adventure", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdventureController {

    @Autowired
    AdventureService adventureService;

    @Autowired
    IUserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Adventure> getAdventureById(@PathVariable("id") Integer id) {
        Adventure adventure = null;

        try {
            adventure = adventureService.getById(id);
            adventure.setReservations(null);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not load images!");
        }

        if(adventure == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no adventure with this id!");

        return new ResponseEntity<>(adventure, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<String> addNewAdventure(Principal user, @RequestBody Adventure adventure) throws IOException {

        if(adventureService.findByName(adventure.getName()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adventure with this name already exists!");

        adventure.getFishingInstructor().setEmail(user.getName());
        adventureService.save(adventure);
        return new ResponseEntity<>("Adventure successfully added!", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<String> updateAdventure(@RequestBody Adventure adventure) throws IOException {
        if(existsAdventureWithSameName(adventure))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adventure with this name already exists!");

        adventureService.update(adventure);
        return new ResponseEntity<>("Successfully edited cottage!", HttpStatus.OK);
    }

    private boolean existsAdventureWithSameName(Adventure adventure) {
        Adventure existedAdventure = adventureService.findByName(adventure.getName());
        return existedAdventure != null && existedAdventure.getId() != adventure.getId();
    }
}
