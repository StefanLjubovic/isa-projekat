package com.backend.repository;

import com.backend.model.Complaint;
import com.backend.model.DeleteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface IComplaintRepository extends JpaRepository<Complaint, Integer> {

    void deleteAllByRentingEntity_Id(Integer id);

    void deleteAllByClient_Id(Integer id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Complaint c where c.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Complaint findOneById(@Param("id") Integer id);
}
