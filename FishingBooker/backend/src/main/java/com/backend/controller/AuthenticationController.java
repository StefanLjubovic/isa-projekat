package com.backend.controller;

import com.backend.dto.JwtAuthenticationRequest;
import com.backend.dto.UserRequest;
import com.backend.dto.UserTokenState;
import com.backend.exception.ResourceConflictException;
import com.backend.model.Admin;
import com.backend.model.RegisteredUser;
import com.backend.service.UserService;
import com.backend.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        RegisteredUser user = (RegisteredUser) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn,user.getRoles().get(0).getName()));
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<RegisteredUser> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {
        RegisteredUser existUser = this.userService.findByEmail(userRequest.getEmail());

        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getId(), "Username already exists");
        }

        RegisteredUser user = this.userService.save(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<Admin> registerNewAdmin(@RequestBody RegisteredUser newAdminUser) {
        if(this.userService.findByEmail(newAdminUser.getEmail()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        Admin addedAdmin = this.userService.saveAdmin(newAdminUser);
        return new ResponseEntity<>(addedAdmin, HttpStatus.CREATED);
    }


}
