package com.backend.controller;

import com.backend.dto.JwtAuthenticationRequest;
import com.backend.dto.UserRequest;
import com.backend.dto.UserTokenState;
import com.backend.exception.ResourceConflictException;
import com.backend.model.VerificationToken;
import com.backend.model.RegistratedUser;
import com.backend.model.RegistrationRequest;
import com.backend.service.MailService;
import com.backend.service.UserService;
import com.backend.service.VerificationTokenService;
import com.backend.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Calendar;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    VerificationTokenService verificationTokenService;

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
        RegistratedUser user = (RegistratedUser) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn,user.getRoles().get(0).getName()));
    }

    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<RegistrationRequest> addUser(@RequestBody UserRequest userRequest, UriComponentsBuilder ucBuilder) {

        RegistratedUser existUser = this.userService.findByEmail(userRequest.getEmail());
        RegistrationRequest user = null;
        if (existUser != null) {
            throw new ResourceConflictException(userRequest.getEmail(), "Username already exists");
        }
        try {
            user = this.userService.saveRequest(userRequest);
            VerificationToken verificationToken = new VerificationToken(String.valueOf(UUID.randomUUID()),user);
            mailService.sendEmail(verificationToken,userRequest.getEmail());
            verificationTokenService.save(verificationToken);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<RegistratedUser> confirmUserAccount(WebRequest request, Model model, @RequestParam("token")String verificationToken) throws Exception {
        VerificationToken token = verificationTokenService.findByToken(verificationToken);
        if (verificationToken == null) {
            throw new Exception("auth.message.invalidToken");
        }

        RegistrationRequest requestReg = token.getUser();
        Calendar cal = Calendar.getInstance();
        if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new Exception("auth.message.expired");
        }
        RegistratedUser user=userService.saveClient(requestReg);
        URI frontend = new URI("http://localhost:8082?id="+user.getId());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(frontend);
        return new ResponseEntity<>(user,httpHeaders, HttpStatus.SEE_OTHER);
    }
}
