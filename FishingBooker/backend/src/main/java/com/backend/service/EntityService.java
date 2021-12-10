package com.backend.service;

import com.backend.dto.EntityDTO;
import com.backend.model.*;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EntityService {

    @Autowired
    IEntityRepository entityRepository;

    @Autowired
    IUserRepository userRepository;

    public EntityService(){ }

    public List<? extends RentingEntity> GetAllEntities(int state){
        List<? extends RentingEntity> entities=null;
        if(state==0)entities=entityRepository.getEntityByClass(Adventure.class);
        else if(state==1)entities=entityRepository.getEntityByClass(Ship.class);
        else if(state==2) entities=entityRepository.getEntityByClass(Cottage.class);
        return entities;
    }

    public List<? extends RentingEntity> GetByUsersSubscriptions(String email) {
        return entityRepository.findSubscriptions(email);
    }

    public void deleteEntity(Integer id) {
        List<Client> clients = userRepository.fetchAllClients();

        for (Client c : clients) {
            for(RentingEntity e : c.getSubscriptions()) {
                if(e.getId() == id){
                    c.getSubscriptions().remove(e);
                    userRepository.save(c);
                    break;
                }
            }
        }

        entityRepository.deleteById(id);
    }

    public Set<Sale> createSaleForEntity(Sale sale, Integer entityId) {
        RentingEntity entity = entityRepository.fetchById(entityId);
        sale.setRentingEntity(entity);
        entity.getSales().add(sale);
        entityRepository.save(entity);
        return entity.getSales();
    }
}
