package com.backend.repository;
import com.backend.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Set;

@Repository
public interface IEntityRepository extends JpaRepository<RentingEntity,Integer> {

    @Query("SELECT e  FROM RentingEntity e LEFT JOIN FETCH e.images where type(e) = ?1")
    <T extends RentingEntity> List<T> getEntityByClass(Class<?> type);

    @Query("select entity" +
            " from RentingEntity entity,Client client LEFT JOIN FETCH entity.images " +
            "where client.email = ?1 and entity in (" +
            "select e from client.subscriptions e)")
    <T extends RentingEntity> List<T> findSubscriptions(String email);

    @Query(value = "select e from RentingEntity e left join fetch e.sales where e.id = :id")
    <T extends RentingEntity> T fetchById(@Param("id") Integer id);

    @Query(value = "select e from RentingEntity e left join fetch e.unavailablePeriods left join fetch e.sales where e.id = :id")
    <T extends RentingEntity> T fetchWithSalesAndPeriods(@Param("id") Integer id);

    @Query(value = "select e from RentingEntity e left join fetch e.unavailablePeriods where e.id = :id")
    <T extends RentingEntity> T fetchWithPeriods(@Param("id") Integer id);

    @Query(value = "select e from RentingEntity e left join fetch e.sales where e.id = :id")
    <T extends RentingEntity> T fetchWithSales(@Param("id") Integer id);

    @Query("select sub" +
            " from Client client left join client.subscriptions sub on sub.id = ?2 where client.email = ?1")
    RentingEntity checkIfSubscribed(String email,Integer entityId);

    @Query(value="select distinct(e) from Sale sale left join sale.rentingEntity e " +
            "left join fetch e.images where type(e) = ?1")
    <T extends RentingEntity> List<T> getEntitiesOnSale(Class<?> type);

    @Query("SELECT e  FROM RentingEntity e LEFT JOIN FETCH e.images LEFT JOIN FETCH e.unavailablePeriods where type(e) = ?1")
    <T extends RentingEntity> List<T> getEntityByClassWithPeriods(Class<?> type);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    RentingEntity findLockedById(Integer id);

    @Query(value = "SELECT c FROM RentingEntity c LEFT JOIN FETCH c.sales where c.name = :name")
    RentingEntity fetchSalesByName(@Param("name") String name);
    @Query(value = "SELECT c FROM RentingEntity c LEFT JOIN FETCH c.unavailablePeriods where c.name = :name")
    RentingEntity fetchUnavailablePeriodsByName(@Param("name") String name);
    @Query(value="SELECT c FROM RentingEntity c LEFT JOIN FETCH c.pricelistItems pl WHERE c.name = :name")
    RentingEntity fetchPricelistItemsByName(@Param("name") String name);

    @Query(value="SELECT c FROM RentingEntity c LEFT JOIN FETCH c.reservations pl WHERE c.name = :name")
    RentingEntity fetchReservationsByName(@Param("name") String name);

    RentingEntity findByName(String name);
}
