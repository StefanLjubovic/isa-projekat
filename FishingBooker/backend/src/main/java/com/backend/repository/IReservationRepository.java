package com.backend.repository;

import com.backend.model.Reservation;
import com.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<Reservation, Integer> {
}
