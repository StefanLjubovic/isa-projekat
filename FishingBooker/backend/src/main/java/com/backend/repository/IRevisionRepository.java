package com.backend.repository;

import com.backend.model.Reservation;
import com.backend.model.Revision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRevisionRepository extends JpaRepository<Revision, Integer> {
}
