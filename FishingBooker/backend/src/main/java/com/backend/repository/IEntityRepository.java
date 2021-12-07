package com.backend.repository;

import com.backend.model.RegisteredUser;
import com.backend.model.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IEntityRepository extends JpaRepository<RentingEntity,Integer> {

    @Query("SELECT e  FROM RentingEntity e where type(e) = ?1")
    <T extends RentingEntity> List<T> getEntityByClass(Class<?> type);

    <T extends RentingEntity> List<T> findByRegisterUsers_Email(String email);
}
