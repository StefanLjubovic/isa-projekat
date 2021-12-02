package com.backend.init_data;

import com.backend.model.Address;
import com.backend.model.Cottage;
import com.backend.repository.ICottageRepository;
import com.backend.repository.IEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private ICottageRepository cottageRepository;

    public DataLoader() {}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Address address = new Address("Bulver oslobodjenja","21","24000","Novi Sad","Srbija");
        Set<String> unallowedBehavior= new HashSet<>();
        unallowedBehavior.add("Zabranjena muzika");
        unallowedBehavior.add("Zabranjeno pravljenje zurki");
        Cottage cottage = new Cottage("Marijina vikendica","Prelepa vikendica uz pogled na jezero",4,address,unallowedBehavior);
        cottageRepository.save(cottage);
        /*Cottage c = cottageRepository.getById(1);
        System.out.println(c.getName());*/
    }
}
