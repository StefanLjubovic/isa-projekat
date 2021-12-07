package com.backend.repository;

import com.backend.model.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {

    RegistrationRequest findByEmail(String email);

    Optional<RegistrationRequest> findById(Integer id);
}
