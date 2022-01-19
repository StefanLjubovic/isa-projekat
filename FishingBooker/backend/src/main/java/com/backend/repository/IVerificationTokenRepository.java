package com.backend.repository;

import com.backend.model.VerificationToken;
import com.backend.model.RegistrationRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface IVerificationTokenRepository extends CrudRepository<VerificationToken, String> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(RegistrationRequest user);
}