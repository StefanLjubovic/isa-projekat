package com.backend.controller;

import com.backend.model.RentingEntity;
import com.backend.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/entity")
public class EntityController {

    @Autowired
    EntityService entityService;

    @GetMapping(value="{state}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<? extends RentingEntity>> GetAllEntities(@PathVariable int state){
        Collection<? extends RentingEntity> entities= entityService.GetAllEntities(state);
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }
}
