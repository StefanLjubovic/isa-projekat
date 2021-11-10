package com.backend.service;

import com.backend.model.Adventure;
import com.backend.model.Cottage;
import com.backend.model.Entity;
import com.backend.model.Ship;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {

    private List<Adventure> adventures;
    private List<Ship> ships;
    private List<Cottage> cottages;
    public ClientService(){
        cottages=new ArrayList<>();
        adventures=new ArrayList<Adventure>();
            adventures.add(new Adventure("pecanje","prelep prikaz reke uz pomoc insturktora", 4.4));
            adventures.add(new Adventure("Pecanje na jezeru","prelep prikaz reke uz pomoc insturktora", 4.2));

        ships=new ArrayList<Ship>();
        ships.add(new Ship("srstarenje","krstarenje obalom mora uz prikaz prelepe prirode", 3.9));
        ships.add(new Ship("Krstarenje na moru" ,"obilazak pecina i zaliva u vremenskom intervalu od 5 sati", 4.6));
        ships.add(new Ship("drstarenje na moru" ,"obilazak pecina i zaliva u vremenskom intervalu od 5 sati", 4.6));
    }

    public List<? extends Entity> GetAllEntities(int state){
        List<? extends Entity> entities=null;
        if(state==0)entities=adventures;
        else if(state==1)entities=ships;
        else if(state==2) entities=cottages;
        return entities;
    }
}
