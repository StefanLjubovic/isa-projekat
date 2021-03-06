package com.backend.repository;

import com.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    List<Role> findByName(String name);

    Role findOneByName(String name);
}
