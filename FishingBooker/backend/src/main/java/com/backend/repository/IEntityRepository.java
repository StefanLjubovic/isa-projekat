package com.backend.repository;
import com.backend.model.RegisteredUser;
import com.backend.model.Adventure;
import com.backend.model.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IEntityRepository extends JpaRepository<RentingEntity,Integer> {

    @Query("SELECT e  FROM RentingEntity e where type(e) = ?1")
    <T extends RentingEntity> List<T> getEntityByClass(Class<?> type);

    @Query("select entity from RentingEntity entity,Client client where client.email = ?1 and entity in (" +
            "select e from client.subscriptions e)")
    <T extends RentingEntity> List<T> findSubscriptions(String email);

    @Query(value = "select e from RentingEntity e left join fetch e.sales where e.id = :id")
    <T extends RentingEntity> T fetchById(@Param("id") Integer id);
}
