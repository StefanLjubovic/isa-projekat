package com.backend.repository;

import com.backend.model.DeleteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDeleteRequestRepository extends JpaRepository<DeleteRequest, Integer> {

    void deleteAllByUser_Id(Integer id);
}
