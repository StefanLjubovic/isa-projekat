package com.backend.service;

import com.backend.model.*;
import com.backend.repository.*;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DeleteRequestService {

    @Autowired
    private IDeleteRequestRepository deleteRequestRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DeleteUserService deleteUserService;


    public void save(String email,String content){
        RegisteredUser user=userRepository.findByEmail(email);
        DeleteRequest deleteRequest = new DeleteRequest(user,content);
        deleteRequestRepository.save(deleteRequest);
    }

    public List<DeleteRequest> getAllRequests() {
        return deleteRequestRepository.findAll();
    }

    public void rejectDeleteRequest(Integer requestId, String response) {
        DeleteRequest deleteRequest = deleteRequestRepository.findById(requestId).get();
        if (deleteRequest == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such request");

        deleteRequestRepository.delete(deleteRequest);
        sendRejectionEmail(deleteRequest.getRegisteredUser().getEmail(), response);
    }

    private void sendRejectionEmail(String email, String response) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your request for deleting account has been rejected by administrator.")
                .append("\nReason for rejection: ").append(response);

        emailService.sendSimpleMessage(email, "Delete Request Rejected", stringBuilder.toString());
    }

    public void approveDeleteRequest(Integer id) {
        DeleteRequest deleteRequest = deleteRequestRepository.findById(id).get();
        RegisteredUser registeredUser = deleteRequest.getRegisteredUser();

        deleteUserService.deleteUser(registeredUser.getId());
        sendApprovalEmail(registeredUser.getEmail());
    }

    private void sendApprovalEmail(String email) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Your request for deleting account has been approved by administrator.")
                .append("\nYour account is now deleted.");

        emailService.sendSimpleMessage(email, "Delete Request Approved", stringBuilder.toString());
    }
}
