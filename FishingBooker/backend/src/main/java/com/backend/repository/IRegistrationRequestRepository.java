package com.backend.repository;

import com.backend.model.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {
}
