package com.backend.controller;

import com.backend.dto.RejectRegistrationDTO;
import com.backend.model.RegistrationRequest;
import com.backend.service.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/regRequest", produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationRequestController {

    @Autowired
    RegistrationRequestService registrationRequestService;

    @GetMapping
    public ResponseEntity<List<RegistrationRequest>> getAllRequests() {
        List<RegistrationRequest> registrationRequests = registrationRequestService.getAllRequests();
        return new ResponseEntity<>(registrationRequests, HttpStatus.OK);
    }

    @GetMapping("/approve/{id}")
    public ResponseEntity<?> approveRegistrationRequest(@PathVariable("id") Integer id) {
        registrationRequestService.approveRegistrationRequest(id);
        return new ResponseEntity<>("Registration request approved!", HttpStatus.CREATED);
    }

    @PutMapping("/reject")
    public ResponseEntity<?> rejectRegistrationRequest(@RequestBody RejectRegistrationDTO rejectRegistrationDTO) {
        registrationRequestService.rejectRegistrationRequest(rejectRegistrationDTO);
        return new ResponseEntity<>("Registration request rejected!", HttpStatus.OK);
    }
}
