package com.backend.controller;

import com.backend.dto.BasicEntityInfoDTO;
import com.backend.model.RentingEntity;
import com.backend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;
    @GetMapping(value="{state}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<? extends RentingEntity>> GetAllEntities(@PathVariable int state){
        Collection<? extends RentingEntity> entities=clientService.GetAllEntities(state);
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }
}
