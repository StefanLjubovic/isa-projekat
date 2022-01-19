package com.backend.repository;

import com.backend.model.PricelistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPricelistItemRepository extends JpaRepository<PricelistItem, Integer> {

    @Query(value = "delete from PricelistItem p where p.rentingEntity.id = :id")
    void deleteByRentingEntityId(Integer id);
}
