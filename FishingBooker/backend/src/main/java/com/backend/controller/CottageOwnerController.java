package com.backend.controller;

import com.backend.dto.EntityDTO;
import com.backend.dto.ReservationHistoryDTO;
import com.backend.model.Cottage;
import com.backend.model.Reservation;
import com.backend.service.Base64ToImage;
import com.backend.service.CottageService;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/cottageOwner", produces = MediaType.APPLICATION_JSON_VALUE)
public class CottageOwnerController {

    @Autowired
    CottageService cottageService;

    private ModelMapper modelMapper = new ModelMapper();
    private Base64ToImage base64ToImage = new Base64ToImage();

    @GetMapping("/cottages")
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
    public ResponseEntity<List<EntityDTO>> getAllCottagesForCottageOwner(Principal owner) throws IOException {
        List<Cottage> cottages = cottageService.getAllCottagesFromCottageOwner(owner.getName());

        List<EntityDTO> dto = new ArrayList<>();
        for(Cottage c : cottages) {
            String[] images = c.getImages().toArray(new String[c.getImages().size()]);

            EntityDTO entityDTO;
            if(images.length > 0)
                entityDTO = new EntityDTO(c.getId(), c.getName(), c.getDescription(), c.getAverageGrade(), base64ToImage.encodeImageToBase64(images[0]), c.getAddress());
            else
                entityDTO = new EntityDTO(c.getId(), c.getName(), c.getDescription(), c.getAverageGrade(), base64ToImage.encodeImageToBase64("/images/undefined/no_image.jpg"), c.getAddress());

            dto.add(entityDTO);
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value="/reservation-history", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('COTTAGE_OWNER')")
    public ResponseEntity<List<ReservationHistoryDTO>> getReservationHistoryForCottageOwner(Principal user){
        List<ReservationHistoryDTO> reservations = this.cottageService.getReservationHistoryForCottageOwner(user.getName());
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
