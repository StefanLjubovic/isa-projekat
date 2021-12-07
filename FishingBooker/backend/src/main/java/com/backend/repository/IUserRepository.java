package com.backend.repository;

import com.backend.model.FishingInstructor;
import com.backend.model.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<RegisteredUser, Integer> {

    RegisteredUser findByEmail(String email);

    @Query(value = "select fi from FishingInstructor fi left join fetch fi.unavailablePeriods where fi.email = :email")
    FishingInstructor fetchByEmail(@Param("email") String email);
}