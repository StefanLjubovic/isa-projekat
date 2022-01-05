package com.backend.repository;

import com.backend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReportRepository extends JpaRepository<Report, Integer> {

    void deleteAllByRentingEntity_Id(Integer id);

    void deleteAllByClient_Id(Integer id);
}
