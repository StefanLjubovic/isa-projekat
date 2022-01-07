package com.backend.repository;

import com.backend.model.RegistrationRequest;
import com.backend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface IRegistrationRequestRepository extends JpaRepository<RegistrationRequest, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    RegistrationRequest save(RegistrationRequest request);

    RegistrationRequest findByEmail(String email);

    Optional<RegistrationRequest> findById(Integer id);
}
