package com.backend.repository;

import com.backend.model.RegistratedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<RegistratedUser, Integer> {
    RegistratedUser findByEmail(String email);
}