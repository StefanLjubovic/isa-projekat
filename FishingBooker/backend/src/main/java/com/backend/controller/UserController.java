package com.backend.controller;

import com.backend.dto.UpdateProfileDTO;
import com.backend.model.RegisteredUser;
import com.backend.model.RentingEntity;
import com.backend.service.EntityService;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value="/getById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> GetById(@PathVariable String id){
        RegisteredUser user=userService.GetById(1);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value="/getLoggedUser",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUser> GetLoggedUser(Principal principal){
        RegisteredUser user=userService.findByEmail(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> UpdateUser(@RequestBody UpdateProfileDTO user){
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RegisteredUser>> getAllUsers() {
        List<RegisteredUser> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}