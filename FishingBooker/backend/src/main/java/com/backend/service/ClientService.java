package com.backend.service;

import com.backend.model.Adventure;
import com.backend.model.Cottage;
import com.backend.model.RentingEntity;
import com.backend.model.Ship;
import com.backend.repository.IEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    IEntityRepository entityRepository;
    public ClientService(){ }

    public List<? extends RentingEntity> GetAllEntities(int state){
        List<? extends RentingEntity> entities=null;
        if(state==0)entities=entityRepository.getEntityByClass(Adventure.class);
        else if(state==1)entities=entityRepository.getEntityByClass(Ship.class);
        else if(state==2) entities=entityRepository.getEntityByClass(Cottage.class);
        return entities;
    }
}
