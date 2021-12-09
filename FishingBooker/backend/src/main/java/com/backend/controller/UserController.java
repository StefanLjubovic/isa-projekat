package com.backend.controller;

import com.backend.dto.RegisteredUserDTO;
import com.backend.dto.UpdateProfileDTO;
import com.backend.model.RegisteredUser;
import com.backend.model.RentingEntity;
import com.backend.service.DeleteRequestService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    DeleteRequestService deleteRequestService;

    @GetMapping(value="/getById/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUserDTO> GetById(@PathVariable Integer id){
        RegisteredUser user = userService.GetById(id);
        RegisteredUserDTO userDTO = new RegisteredUserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail(), user.getPassword(), user.getStatus(), user.isEnabled(), user.getRole(), user.getLastPasswordResetDate(), user.getAddress());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value="/getLoggedUser",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisteredUserDTO> GetLoggedUser(Principal principal){
        RegisteredUserDTO user=userService.fetchMyProfileInfo(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> UpdateUser(@RequestBody UpdateProfileDTO user){
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping (value="/changePassword/{password}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> ChangePassword(@PathVariable String password, Principal principal){
        userService.updatePasswod(principal.getName(),password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RegisteredUserDTO>> getAllUsers() {
        List<RegisteredUser> users = this.userService.getAllUsers();

        List<RegisteredUserDTO> dto = new ArrayList<>();
        for(RegisteredUser u : users) {
            RegisteredUserDTO userDTO = new RegisteredUserDTO(u.getId(),u.getFirstName(), u.getLastName(), u.getPhoneNumber(), u.getEmail(), u.getPassword(), u.getStatus(), u.isEnabled(), u.getRole(), u.getLastPasswordResetDate(), u.getAddress());
            dto.add(userDTO);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/deleteRequest")
    public ResponseEntity<Void> saveDeleteRequest(@RequestBody String content,Principal principal) {
        deleteRequestService.save(principal.getName(),content);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}