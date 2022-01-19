package com.backend.controller;

import com.backend.dto.EntityDTO;
import com.backend.dto.ReservationHistoryDTO;
import com.backend.dto.ReservationIncomeDTO;
import com.backend.model.Ship;
import com.backend.service.Base64ToImage;
import com.backend.service.ShipOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/shipOwner", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShipOwnerController {

    @Autowired
    private ShipOwnerService shipOwnerService;

    private Base64ToImage base64ToImage = new Base64ToImage();

    @GetMapping("/ships")
    @PreAuthorize("hasRole('SHIP_OWNER')")
    public ResponseEntity<List<EntityDTO>> getAllShipsFromShipOwner(Principal owner) throws IOException {
        List<Ship> ships = this.shipOwnerService.getAllShipsFromShipOwner(owner.getName());

        List<EntityDTO> dto = new ArrayList<>();
        for(Ship s: ships) {
            String[] images = s.getImages().toArray(new String[s.getImages().size()]);

            EntityDTO entityDTO;
            if(images.length > 0)
                entityDTO = new EntityDTO(s.getId(), s.getName(), s.getDescription(), s.getAverageGrade(), base64ToImage.encodeImageToBase64(images[0]), s.getAddress());
            else
                entityDTO = new EntityDTO(s.getId(), s.getName(), s.getDescription(), s.getAverageGrade(), base64ToImage.encodeImageToBase64("/images/undefined/no_image.jpg"), s.getAddress());

            dto.add(entityDTO);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value="/reservation-history", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SHIP_OWNER')")
    public ResponseEntity<List<ReservationHistoryDTO>> getReservationHistoryForShipOwner(Principal user){
        List<ReservationHistoryDTO> reservations = this.shipOwnerService.getReservationHistoryForShipOwner(user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping(value="/reservation-income", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('SHIP_OWNER')")
    public ResponseEntity<List<ReservationIncomeDTO>> getReservationIncomeForCottageOwner(Principal user){
        List<ReservationIncomeDTO> reservations = this.shipOwnerService.calculateReservationIncomeForShips(user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
