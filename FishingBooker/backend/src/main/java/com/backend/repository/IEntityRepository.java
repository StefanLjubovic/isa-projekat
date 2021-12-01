package com.backend.repository;

import com.backend.dto.BasicEntityInfoDTO;
import com.backend.model.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IEntityRepository extends JpaRepository<RentingEntity,Integer> {



    @Query("SELECT new com.backend.model.RentingEntity(e.id ,e.name,e.description,e.address,e.averageGrade)  FROM " +
            "RentingEntity e where type(e) = ?1")
    <T extends RentingEntity> List<T> getEntityByClass(Class<?> type);
}
