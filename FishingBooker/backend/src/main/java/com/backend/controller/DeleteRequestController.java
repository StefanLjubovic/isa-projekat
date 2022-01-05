package com.backend.controller;

import com.backend.dto.DeleteRequestDTO;
import com.backend.model.DeleteRequest;
import com.backend.service.DeleteRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/deleteRequest", produces = MediaType.APPLICATION_JSON_VALUE)
public class DeleteRequestController {

    @Autowired
    DeleteRequestService deleteRequestService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN', 'COTTAGE_OWNER', 'SHIP_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<Void> saveDeleteRequest(@RequestBody String content, Principal principal) {
        deleteRequestService.save(principal.getName(), content);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Set<DeleteRequestDTO>> getAllDeleteRequests() {
        List<DeleteRequest> requests = deleteRequestService.getAllRequests();

        Set<DeleteRequestDTO> DTOs = new HashSet<>();
        for (DeleteRequest r : requests) {
            DeleteRequestDTO dto = new DeleteRequestDTO(r.getId(), r.getRegisteredUser().getEmail(), r.getRegisteredUser().getFirstName(), r.getRegisteredUser().getLastName(), r.getRegisteredUser().getPhoneNumber(), r.getRegisteredUser().getRole().getName(), r.getContent());
            DTOs.add(dto);
        }
        return new ResponseEntity<>(DTOs, HttpStatus.OK);
    }

    @PutMapping("/reject/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> rejectDeleteRequest(@PathVariable("id") Integer id, @RequestBody String response) {
        deleteRequestService.rejectDeleteRequest(id, response);
        return new ResponseEntity<>("Delete request rejected!", HttpStatus.OK);
    }

    @PutMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveDeleteRequest(@RequestBody Integer id) {
        deleteRequestService.approveDeleteRequest(id);
        return new ResponseEntity<>("Delete request approved", HttpStatus.OK);
    }
}
