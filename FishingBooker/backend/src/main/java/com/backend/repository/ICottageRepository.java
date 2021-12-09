package com.backend.repository;

import com.backend.model.Cottage;
import com.backend.model.FishingInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICottageRepository extends JpaRepository<Cottage,Integer> {

    Cottage findByName(String name);

    @Query(value="SELECT c FROM Cottage c LEFT JOIN FETCH c.pricelistItem pl WHERE c.id =: id")
    Cottage fetchById(@Param("id") int id);
}
