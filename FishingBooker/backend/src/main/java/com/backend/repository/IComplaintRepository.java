package com.backend.repository;

import com.backend.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComplaintRepository extends JpaRepository<Complaint, Integer> {

    void deleteAllByRentingEntity_Id(Integer id);

    void deleteAllByClient_Id(Integer id);
}
