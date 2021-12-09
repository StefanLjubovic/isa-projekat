package com.backend.repository;

import com.backend.model.PricelistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPricelistItemRepository extends JpaRepository<PricelistItem, Integer> {
}
