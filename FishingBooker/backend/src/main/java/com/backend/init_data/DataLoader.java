package com.backend.init_data;

import com.backend.model.*;
import com.backend.repository.ICottageRepository;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IRoleRepository;
import com.backend.repository.IUserRepository;
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
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    public DataLoader() {}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Address address = new Address("Bulver oslobodjenja","21","24000","Novi Sad","Srbija");
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
        List<Role> roles = roleRepository.findByName("ROLE_ADMIN");
        RegisteredUser user = new RegisteredUser("Adam", "Adamovic", "064656565", "mainadmin@gmail.com", "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", UserStatus.active, true, roles, new Timestamp(System.currentTimeMillis()), address1);
        Admin admin = new Admin(user, false);
        userRepository.save(admin);
    }
}
