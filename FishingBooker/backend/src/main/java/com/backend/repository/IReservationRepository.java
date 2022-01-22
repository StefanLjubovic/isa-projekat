package com.backend.repository;

import com.backend.dto.ReservationDTO;
import com.backend.dto.ReservationHistoryDTO;
import com.backend.model.Reservation;
import com.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    Reservation save(Reservation reservation);

    @Query("SELECT new com.backend.dto.ReservationDTO(r.id,r.dateTime,r.durationInHours,r.maxPersons,r.price,r.isCanceled,r.rentingEntity.id,r.rentingEntity.name)  " +
            "FROM Reservation r where r.client.email = ?1 and r.isCanceled = false and r.dateTime > ?2")
    List<ReservationDTO> fetchByClientEmail(String email, Date date);

    @Query("Select r FROM Reservation r left join fetch r.rentingEntity e left join fetch e.unavailablePeriods where r.id=?1")
    Reservation fetchWithUnavailablePeriods(Integer id);

    @Query("SELECT new com.backend.dto.ReservationDTO(r.id,r.dateTime,r.durationInHours,r.maxPersons,r.price,r.isCanceled,r.rentingEntity.id,r.rentingEntity.name)  " +
            "FROM Reservation r left join r.rentingEntity e WHERE type(e) = ?3 and r.client.email = ?1 and r.isCanceled = false and r.dateTime < ?2")
    List<ReservationDTO> fetchHistoryByClientEmail(String email, Date date,Class<?> type);

    @Query("select r from Reservation r left join fetch r.rentingEntity where r.rentingEntity.id = :id")
    List<Reservation> fetchByEntityId(@Param("id") Integer id);

    @Query("SELECT r FROM Reservation r left join fetch r.rentingEntity WHERE r.rentingEntity.name = :name and r.isCanceled = false")
    List<Reservation> fetchByEntityName(@Param("name") String name);

    List<Reservation> getReservationByRentingEntity_Id(Integer id);

    List<Reservation> getReservationsByClient_Id(Integer id);

    @Query("SELECT new com.backend.dto.ReservationHistoryDTO(r.id, r.dateTime, r.durationInHours, r.price, r.rentingEntity.id, r.rentingEntity.name, r.client.email) " +
            "FROM Reservation r WHERE r.rentingEntity.name = :name and r.isCanceled = false ORDER BY r.dateTime")
    List<ReservationHistoryDTO> fetchReservationHistoryByEntityName(@Param("name") String name);

    void deleteAllByClient_Id(Integer id);
}
