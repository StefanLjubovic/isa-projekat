package com.backend.controller;

import com.backend.model.UnavailablePeriod;
import com.backend.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(value = "/instructor", produces = MediaType.APPLICATION_JSON_VALUE)
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping("/unavailablePeriod")
    //@PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<?> defineUnavailablePeriodForInstructor(@RequestBody UnavailablePeriod unavailablePeriod, Principal user) {
        instructorService.defineUnavailablePeriodForInstructor(unavailablePeriod, user.getName());
        return new ResponseEntity<>("Successfully defined!", HttpStatus.OK);
    }
}
