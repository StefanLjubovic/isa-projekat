package com.backend.service;

import com.backend.model.Client;
import com.backend.model.DeleteRequest;
import com.backend.model.RegisteredUser;
import com.backend.repository.IDeleteRequestRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteRequestService {

    @Autowired
    IDeleteRequestRepository deleteRequestRepository;

    @Autowired
    IUserRepository userRepository;

    public void save(String email,String content){
        RegisteredUser user=userRepository.findByEmail(email);
        DeleteRequest deleteRequest = new DeleteRequest(user,content);
        deleteRequestRepository.save(deleteRequest);
    }

    public List<DeleteRequest> getAllRequests() {
        return deleteRequestRepository.findAll();
    }
}
