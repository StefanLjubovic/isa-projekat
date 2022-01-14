package com.backend.service;

import com.backend.dto.ComplaintDTO;
import com.backend.model.Client;
import com.backend.model.Complaint;
import com.backend.model.RegisteredUser;
import com.backend.repository.IComplaintRepository;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    IComplaintRepository complaintRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IEntityRepository entityRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    AdvertiserService advertiserService;

    @Transactional(readOnly = false)
    public void createComplaint(ComplaintDTO dto, String email) {
        Complaint complaint = new Complaint(dto.getContent(),new Client(userRepository.findByEmail(email)),entityRepository.findById(dto.getEntityId()).get());
        complaintRepository.save(complaint);
    }

    @Transactional(readOnly = true)
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void respondToComplaint(Integer complaintId, String response) {
        Complaint complaint = complaintRepository.findOneById(complaintId);
        if(complaint == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such complaint!");

        sendResponseEmail(complaint, response);
        complaintRepository.deleteById(complaint.getId());
    }

    private void sendResponseEmail(Complaint complaint, String response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Client ").append(complaint.getClient().getFullName()).append(" complained to ")
                .append(complaint.getRentingEntity().getName()).append(".")
                .append("\n\nComplaint: ").append(complaint.getContent())
                .append("\nAdmin's response: ").append(response);

        emailService.sendSimpleMessage(complaint.getClient().getEmail(), "RESPONSE TO COMPLAINT", stringBuilder.toString());

        RegisteredUser advertiser = advertiserService.findAdvertiserByEntityId(complaint.getRentingEntity().getId());
        emailService.sendSimpleMessage(advertiser.getEmail(), "RESPONSE TO COMPLAINT", stringBuilder.toString());
    }
}
