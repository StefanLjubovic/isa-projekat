package com.backend.service;

import com.backend.dto.RejectRegistrationDTO;
import com.backend.model.*;
import com.backend.repository.IAddressRepository;
import com.backend.repository.IRegistrationRequestRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class RegistrationRequestService {

    @Autowired
    IRegistrationRequestRepository registrationRequestRepository;

    @Autowired
    IAddressRepository addressRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    AdminEmailService emailService;

    public List<RegistrationRequest> getAllRequests() {
        return registrationRequestRepository.findAll();
    }

    public void approveRegistrationRequest(Integer id) {
        RegistrationRequest request = registrationRequestRepository.getById(id);
        if(request == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no such request!");

        registrationRequestRepository.delete(request);
        saveUser(request);
        sendApprovalEmail(request);
    }

    public void rejectRegistrationRequest(RejectRegistrationDTO rejectRegistrationDTO) {
        RegistrationRequest request = registrationRequestRepository.getById(rejectRegistrationDTO.getId());
        if(request == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no such request!");

        registrationRequestRepository.delete(request);
        sendRejectionEmail(request, rejectRegistrationDTO.getRejectionReason());
    }

    private void sendRejectionEmail(RegistrationRequest request, String rejectionReason) {
        StringBuilder sb = new StringBuilder("");
        sb.append("Hello, ").append(request.getFirstName()).append(" ").append(request.getLastName())
                .append("!\n We are sorry to inform you that your registration request on FishingBooker has been rejected.\n")
                .append("Reason for rejection: \n")
                .append(rejectionReason);
        emailService.sendSimpleMessage(request.getEmail(), "Registration request rejected!", sb.toString());
    }

    private void sendApprovalEmail(RegistrationRequest request) {
        StringBuilder sb = new StringBuilder("");
        sb.append("Hello there, ").append(request.getFirstName()).append(" ").append(request.getLastName())
                .append("!\n Your registration request on FishingBooker has been approved. We are happy to have you here!");
        emailService.sendSimpleMessage(request.getEmail(), "Registration request approved!", sb.toString());
    }

    private void saveUser(RegistrationRequest request) {
        RegisteredUser user = createUserFromRegistrationRequest(request);

        if(request.getRole().getName().equals("ROLE_COTTAGE_OWNER")) {
            saveCottageOwner(user);
        } else if(request.getRole().getName().equals("ROLE_SHIP_OWNER")) {
            saveShipOwner(user);
        } else if(request.getRole().getName().equals("ROLE_INSTRUCTOR")) {
            saveFishingInstructor(request, user);
        }
    }

    private void saveFishingInstructor(RegistrationRequest request, RegisteredUser user) {
        FishingInstructor fishingInstructor = new FishingInstructor(user, request.getBiography());
        userRepository.save(fishingInstructor);
    }

    private void saveShipOwner(RegisteredUser user) {
        ShipOwner shipOwner = new ShipOwner(user);
        userRepository.save(shipOwner);
    }

    private void saveCottageOwner(RegisteredUser user) {
        CottageOwner cottageOwner = new CottageOwner(user);
        userRepository.save(cottageOwner);
    }

    private RegisteredUser createUserFromRegistrationRequest(RegistrationRequest request) {
        return new RegisteredUser(request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPassword(),
                UserStatus.active,
                true,
                roleService.findOneByName(request.getRole().getName()),
                new Timestamp(System.currentTimeMillis()),
                request.getAddress());
    }
}
