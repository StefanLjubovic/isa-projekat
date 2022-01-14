package com.backend.controller;

import com.backend.dto.ShipDTO;
import com.backend.dto.UnavailablePeriodDTO;
import com.backend.dto.UpdateShipDTO;
import com.backend.model.Ship;
import com.backend.model.UnavailablePeriod;
import com.backend.service.ShipService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value="/ship", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShipController {

    @Autowired
    private ShipService shipService;
    private ModelMapper modelMapper = new ModelMapper();

    public ShipController() {}

    @GetMapping("/getOne/{id}")
    public Ship getOne(@PathVariable("id") Integer id) throws IOException {
        return shipService.findById(id);
    }

    @PostMapping("/defineUnavailablePeriod")
    @PreAuthorize("hasRole('SHIP_OWNER')")
    public ResponseEntity<UnavailablePeriodDTO> defineUnavailablePeriodForShip(@RequestBody UnavailablePeriodDTO unavailablePeriodDTO, Principal user) throws ResponseStatusException{
        UnavailablePeriod period = this.shipService.defineUnavailablePeriodForShip(unavailablePeriodDTO);
        UnavailablePeriodDTO dto = new UnavailablePeriodDTO(period.getFromDateTime(), period.getToDateTime(), "Unavailable period created!");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('SHIP_OWNER')")
    public ResponseEntity<String> addNewShip(Principal user, @RequestBody ShipDTO shipDTO) throws IOException {
        Ship ship = modelMapper.map(shipDTO, Ship.class);
        if(shipService.findByName(ship.getName()) != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ship with this name already exists!");

        ship.getShipOwner().setEmail(user.getName());
        this.shipService.save(ship);
        return new ResponseEntity<>("Successfully added ship!", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('SHIP_OWNER')")
    public ResponseEntity<String> updateShip(Principal user, @RequestBody UpdateShipDTO updateShipDTO) throws IOException {
        Ship ship = modelMapper.map(updateShipDTO, Ship.class);
        if(existShipeWithSameName(ship))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ship with this name already exists!");

        this.shipService.update(ship);
        return new ResponseEntity<>("Successfully edited ship!", HttpStatus.OK);
    }

    private boolean existShipeWithSameName(Ship ship) {
        Ship existedShip = this.shipService.findByName(ship.getName());
        return existedShip != null && existedShip.getId() != ship.getId();
    }
}
