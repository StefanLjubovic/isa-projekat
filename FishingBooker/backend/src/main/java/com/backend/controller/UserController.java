package com.backend.controller;

import com.backend.model.RegistratedUser;
import com.backend.model.RentingEntity;
import com.backend.service.ClientService;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    @GetMapping(value="{state}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<? extends RentingEntity>> GetAllEntities(@PathVariable int state){
        Collection<? extends RentingEntity> entities=clientService.GetAllEntities(state);
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping(value="/getById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistratedUser> GetById(@PathVariable String id){
        RegistratedUser user=userService.GetById(1);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping(value="/getLoggedUser",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistratedUser> GetLoggedUser(Principal principal){
        RegistratedUser user=userService.findByEmail(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
