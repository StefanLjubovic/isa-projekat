package com.backend.repository;

import com.backend.model.SystemProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ISystemPropertyRepository extends JpaRepository<SystemProperty, Integer> {

    @Query("select sp from SystemProperty sp where sp.key = 'INCOME_PERCENTAGE'")
    SystemProperty getIncomePercentage();

    @Modifying
    @Query("update SystemProperty sp set sp.value = ?1 where sp.key = 'INCOME_PERCENTAGE'")
    void updateIncomePercentage(String percentage);
}
