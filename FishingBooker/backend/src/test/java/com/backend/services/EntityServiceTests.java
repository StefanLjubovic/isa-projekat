package com.backend.services;

import com.backend.model.*;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IUserRepository;
import com.backend.service.EntityService;
import com.backend.service.ReservationService;
import com.backend.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.backend.constants.AddressConstants.*;
import static com.backend.constants.EntityConstants.*;
import static com.backend.constants.RegisteredUserConstants.*;
import static com.backend.constants.ReservationConstants.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityServiceTests {

    @Mock
    private IEntityRepository entityRepository;

    @InjectMocks
    private EntityService entityService;

    @Test
    public void checkIfOverlaping(){
        Address address=new Address(DB_STREET_NAME,DB_STREET_NUMBER,DB_POSTAL_CODE,DB_CITY,DB_COUNTRY);

        RegisteredUser user = new RegisteredUser(DB_ID,DB_FIRST_NAME,DB_LAST_NAME,DB_PHONE_NUMBER,DB_EMAIL,DB_PASSWORD,DB_USER_STATUS,DB_ENABLED,new Role(DB_ID,DB_ROLE_NAME),DB_DATE,address);

        Client client = new Client(user);

        RentingEntity entity = new RentingEntity(DB_ENTITY_NAME,DB_ENTITY_DESCRIPTION,DB_ENTITY_GRADE,address,new HashSet<>());
        entity.getUnavailablePeriods().add(new UnavailablePeriod(DB_ID,DB_UNAVAILABLE_PERIOD_START,DB_UNAVAILABLE_PERIOD_END));

        Reservation reservation = new Reservation(DB_ID,DB_RESERVATION_DATE,DB_RESERVATION_DURATION,DB_MAX_PERSONS,DB_PRICE,client,entity);

        Reservation reservationUpdated = entityService.checkIfAlreadyReserved(reservation);
        Assert.isNull(reservationUpdated);
    }

    @Test
    public void  getAllEntitiesTest(){
        when(entityRepository.getEntityByClass(Adventure.class)).thenReturn(null);
        List<? extends RentingEntity> entities=entityService.GetAllEntities(0);
        Assert.isNull(entities);
    }

}
