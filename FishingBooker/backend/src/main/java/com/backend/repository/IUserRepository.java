package com.backend.repository;

import com.backend.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<RegisteredUser, Integer> {
    RegisteredUser findByEmail(String email);
}