package com.backend.repository;

import com.backend.model.Adventure;
import com.backend.model.Cottage;
import com.backend.model.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICottageRepository extends JpaRepository<Cottage,Integer> {

    Cottage findByName(String name);

    List<Cottage> getCottagesByCottageOwner_Email(String id);

    @Query(value = "SELECT c FROM Cottage c LEFT JOIN FETCH c.sales")
    Cottage fetchAllSales();
    @Query(value = "SELECT c FROM Cottage c LEFT JOIN FETCH c.unavailablePeriods where c.name = :name")
    Cottage fetchUnavailablePeriodsByName(@Param("name") String name);
    @Query(value="SELECT c FROM Cottage c LEFT JOIN FETCH c.pricelistItems pl WHERE c.name = :name")
    Cottage fetchPricelistItemsByName(@Param("name") String name);
    @Query(value="SELECT c FROM Cottage c LEFT JOIN FETCH c.rooms pl WHERE c.name = :name")
    Cottage fetchRoomsByName(@Param("name") String name);

    @Query("select c from Cottage c left join fetch c.cottageOwner where c.id = :id")
    Cottage fetchOwnerByCottageId(@Param("id") Integer id);

    List<Cottage> getCottagesByCottageOwner_Id(Integer id);
}
