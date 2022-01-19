package com.backend.repository;

import com.backend.model.Adventure;
import com.backend.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAdventureRepository extends JpaRepository<Adventure, Integer> {

    List<Adventure> getAdventuresByFishingInstructor_Email(String id);

    Adventure findAdventureByName(String name);

    @Query(value = "select a from Adventure a left join fetch a.sales where a.fishingInstructor.email = :email")
    List<Adventure> fetchAdventuresByFishingInstructor_Email(@Param("email") String email);

    @Query(value = "select a from Adventure a left join fetch a.pricelistItems left join fetch a.sales where a.id = :id")
    Adventure fetchById(@Param("id") Integer id);

    @Query("select a from Adventure a left join fetch a.fishingInstructor where a.fishingInstructor.email = :email")
    List<Adventure> getAdventureByFishingInstructorEmail(@Param("email") String email);

    @Query("select a from Adventure a left join fetch a.fishingInstructor where a.id = :id")
    Adventure fetchInstructorByAdventureId(@Param("id") Integer id);

    List<Adventure> getAdventuresByFishingInstructor_Id(Integer id);
}
