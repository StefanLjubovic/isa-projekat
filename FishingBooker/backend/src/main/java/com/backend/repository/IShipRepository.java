package com.backend.repository;

import com.backend.model.Cottage;
import com.backend.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IShipRepository extends JpaRepository<Ship, Integer> {

    List<Ship> getShipsByShipOwner_Email(String email);

    @Query(value = "SELECT s FROM Ship s LEFT JOIN FETCH s.sales")
    Ship fetchAllSales();
    @Query(value = "SELECT s FROM Ship s LEFT JOIN FETCH s.unavailablePeriods where s.name = :name")
    Ship fetchUnavailablePeriodsByName(@Param("name") String name);
    @Query(value="SELECT s FROM Ship s LEFT JOIN FETCH s.pricelistItems pl WHERE s.name = :name")
    Ship fetchPricelistItemsByName(@Param("name") String name);
}
