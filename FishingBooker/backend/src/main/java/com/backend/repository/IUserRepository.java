package com.backend.repository;

import com.backend.dto.RegisteredUserDTO;
import com.backend.model.Client;
import com.backend.model.FishingInstructor;
import com.backend.model.RegisteredUser;
import com.backend.model.RentingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<RegisteredUser, Integer> {

    RegisteredUser findByEmail(String email);

    RegisteredUser getByEmail(String email);

    @Query("select new com.backend.dto.RegisteredUserDTO(u) from RegisteredUser u where u.email = ?1")
    RegisteredUserDTO myProfileInformation(String email);

    @Query(value = "select fi from FishingInstructor fi left join fetch fi.unavailablePeriods where fi.email = :email")
    FishingInstructor fetchByEmail(@Param("email") String email);

    @Query(value = "select fi from FishingInstructor fi left join fetch fi.unavailablePeriods where fi.id = :id")
    FishingInstructor fetchById(@Param("id") Integer id);

    @Query(value = "select c from Client c left join fetch c.subscriptions")
    List<Client> fetchAllClients();

    @Query(value="select client from Client client left join fetch client.subscriptions where client.email = ?1" )
    Client fetchClientWithSubscriptions(String email);

    @Query(value = "select client from Client client where ?1 in (select s.id from client.subscriptions s)")
    List<Client> fetchClientsSubscribedToEntity(Integer id);
}