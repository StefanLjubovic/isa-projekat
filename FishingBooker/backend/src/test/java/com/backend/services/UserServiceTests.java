package com.backend.services;

import com.backend.model.*;
import com.backend.repository.IUserRepository;
import com.backend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import static com.backend.constants.AddressConstants.*;
import static com.backend.constants.EntityConstants.*;
import static com.backend.constants.RegisteredUserConstants.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class )
@SpringBootTest
public class UserServiceTests {

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testUnsubscribe(){
        Address address=new Address(DB_STREET_NAME,DB_STREET_NUMBER,DB_POSTAL_CODE,DB_CITY,DB_COUNTRY);

        RegisteredUser user = new RegisteredUser(DB_ID,DB_FIRST_NAME,DB_LAST_NAME,DB_PHONE_NUMBER,DB_EMAIL,DB_PASSWORD,DB_USER_STATUS,DB_ENABLED,new Role(DB_ID,DB_ROLE_NAME),DB_DATE,address);
        Client client = new Client(user);
        RentingEntity entity = new RentingEntity(DB_ID,DB_ENTITY_NAME,DB_ENTITY_DESCRIPTION,DB_ENTITY_GRADE,address);

        client.getSubscriptions().add(entity);

        when(userRepository.fetchClientWithSubscriptions(DB_EMAIL)).thenReturn(client);

        boolean response=userService.alterSubscriptions(DB_EMAIL,DB_ID);
        Assert.isTrue(response);
    }
}
