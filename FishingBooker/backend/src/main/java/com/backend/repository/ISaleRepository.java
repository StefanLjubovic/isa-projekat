package com.backend.repository;

import com.backend.model.RegisteredUser;
import com.backend.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleRepository extends JpaRepository<Sale, Integer> {
}
