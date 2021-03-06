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
        cottageRepository.save(cottage);
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_CLIENT"));
        roleRepository.save(new Role("ROLE_COTTAGE_OWNER"));
        roleRepository.save(new Role("ROLE_INSTRUCTOR"));
        roleRepository.save(new Role("ROLE_SHIP_OWNER"));

        Address address1 = new Address("Bulver Kralja Petra","11","21000","Novi Sad","Srbija");
        Role role = roleRepository.findOneByName("ROLE_ADMIN");
        RegisteredUser user = new RegisteredUser("Adam", "Adamovic", "064656565", "mainadmin@gmail.com", "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", UserStatus.active, true, role, new Timestamp(System.currentTimeMillis()), address1);
        Admin admin = new Admin(user, false);
        userRepository.save(admin);

        Address address2 = new Address("Bulver Despota Stefana","5a","21000","Novi Sad","Srbija");
        Role roleCottageOwner = roleRepository.findOneByName("ROLE_COTTAGE_OWNER");
        RegisteredUser userCottageOwner = new RegisteredUser("Mika", "Mikic", "0641234567", "mikamikic@gmail.com", "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", UserStatus.active, true, roleCottageOwner, new Timestamp(System.currentTimeMillis()), address2);
        CottageOwner cottageOwner = new CottageOwner(userCottageOwner);
        userRepository.save(cottageOwner);

        SystemProperty systemProperty = new SystemProperty("INCOME_PERCENTAGE", "0");
        this.systemPropertyRepository.save(systemProperty);*/
    }
}
