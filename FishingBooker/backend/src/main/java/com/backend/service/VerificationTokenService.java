package com.backend.service;

import com.backend.model.RegistrationRequest;
import com.backend.model.VerificationToken;
import com.backend.repository.IVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService {

    @Autowired
    private IVerificationTokenRepository verificationTokenRepository;

    public void createVerificationToken(RegistrationRequest user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    public void DeleteTokenByUser(RegistrationRequest user){
        verificationTokenRepository.delete(verificationTokenRepository.findByUser(user));
    }

    public VerificationToken findByToken(String name){
        return verificationTokenRepository.findByToken(name);
    }

    public void save(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }
}
