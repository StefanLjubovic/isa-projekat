package com.backend.repository;

import com.backend.dto.ReservationDTO;
import com.backend.model.Reservation;
import com.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT new com.backend.dto.ReservationDTO(r.id,r.dateTime,r.durationInHours,r.maxPersons,r.price,r.isCanceled,r.rentingEntity.id,r.rentingEntity.name)  " +
            "FROM Reservation r where r.client.email = ?1 and r.isCanceled = false and r.dateTime > ?2")
    List<ReservationDTO> fetchByClientEmail(String email, Date date);


    @Query("Select r FROM Reservation r left join fetch r.rentingEntity e left join fetch e.unavailablePeriods where r.id=?1")
    Reservation fetchWithUnavailablePeriods(Integer id);

    @Query("SELECT new com.backend.dto.ReservationDTO(r.id,r.dateTime,r.durationInHours,r.maxPersons,r.price,r.isCanceled,r.rentingEntity.id,r.rentingEntity.name)  " +
            "FROM Reservation r left join r.rentingEntity e WHERE type(e) = ?3 and r.client.email = ?1 and r.isCanceled = false and r.dateTime < ?2")
    List<ReservationDTO> fetchHistoryByClientEmail(String email, Date date,Class<?> type);
}
