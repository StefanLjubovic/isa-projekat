package com.backend.repository;

import com.backend.model.SystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISystemPropertyRepository extends JpaRepository<SystemProperty, Integer> {
}
