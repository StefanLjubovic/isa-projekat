package com.backend.repository;

import com.backend.model.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICottageRepository extends JpaRepository<Cottage,Integer> {
}
