package com.backend.service;

import com.backend.model.Adventure;
import com.backend.model.RentingEntity;
import com.backend.model.Sale;
import com.backend.repository.IAdventureRepository;
import com.backend.repository.IEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SaleService {

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private IAdventureRepository adventureRepository;

    public Sale createSaleForEntity(Sale sale, Integer entityId) {
        RentingEntity entity = entityRepository.fetchById(entityId);
        sale.setRentingEntity(entity);
        entity.getSales().add(sale);
        entityRepository.save(entity);
        return sale;
    }

    public Set<Sale> getAllSalesForLoggedInstructor(String email) {
        List<Adventure> adventures = adventureRepository.fetchAdventuresByFishingInstructor_Email(email);

        Set<Sale> sales = new HashSet<>();
        for(Adventure a : adventures) {
            for(Sale s : a.getSales()) {
                s.setRentingEntity(a);
                sales.add(s);
            }
        }
        return sales;
    }
}
