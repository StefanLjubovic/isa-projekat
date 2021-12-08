package com.backend.repository;

import com.backend.model.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAdventureRepository extends JpaRepository<Adventure, Integer> {

    List<Adventure> getAdventuresByFishingInstructorId(Integer id);

    @Query(value = "select a from Adventure a left join fetch a.pricelistItems left join fetch a.sales where a.id = :id")
    Adventure fetchById(@Param("id") Integer id);
}
