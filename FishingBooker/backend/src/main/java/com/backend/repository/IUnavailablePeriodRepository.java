package com.backend.repository;

import com.backend.model.UnavailablePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUnavailablePeriodRepository extends JpaRepository<UnavailablePeriod, Integer> {
}
