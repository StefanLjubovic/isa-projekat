package com.backend.repository;

import com.backend.model.Revision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRevisionRepository extends JpaRepository<Revision, Integer> {

    void deleteAllByReservation_Id(Integer id);

    @Query(value = "select avg(r.mark) from Revision r where r.reservation.rentingEntity.id = :id and r.isApproved = true")
    double getAverageGradeOfEntityById(@Param("id") Integer id);
}
