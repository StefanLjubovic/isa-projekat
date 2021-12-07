package com.backend.init_data;

import com.backend.model.*;
import com.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private ICottageRepository cottageRepository;

    @Autowired
    private IAdventureRepository adventureRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ISystemPropertyRepository systemPropertyRepository;

    public DataLoader() {}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*
        Set<String> unallowedBehavior= new HashSet<>();
        unallowedBehavior.add("Zabranjena muzika");
        unallowedBehavior.add("Zabranjeno pravljenje zurki");
        Cottage cottage = new Cottage("Marijina vikendica","Prelepa vikendica uz pogled na jezero",4,address,unallowedBehavior);
        cottageRepository.save(cottage);*/

        /*


        /*
        Set<String> allowedBehavior= new HashSet<>();
        allowedBehavior.add("Bringing pets to the adventure.");
        allowedBehavior.add("Alcohol is allowed");
        Adventure adventure = new Adventure("Fishing in the sunset", "Beautiful moments with beautiful nature and sun", 0, address3, 0, allowedBehavior, unallowedBehavior, 5, fishingInstructor);
        adventureRepository.save(adventure);*/

    }
}
