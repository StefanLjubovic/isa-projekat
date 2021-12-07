package com.backend.repository;

import com.backend.model.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAdventureRepository extends JpaRepository<Adventure, Integer> {

    List<Adventure> getAdventuresByFishingInstructorId(Integer id);
}
