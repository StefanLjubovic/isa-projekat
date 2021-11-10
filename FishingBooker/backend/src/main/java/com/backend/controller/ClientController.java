package com.backend.controller;

import com.backend.model.Entity;
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

    @GetMapping(value="/test",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entity> Test(){
        Entity e=new Entity("aa","bb",4.0);
        return new ResponseEntity<Entity>(e, HttpStatus.OK);
    }
    @GetMapping(value="{state}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<? extends Entity>> GetAllEntities(@PathVariable int state){
        return new ResponseEntity<Collection<? extends Entity>>(clientService.GetAllEntities(state), HttpStatus.OK);
    }
}
