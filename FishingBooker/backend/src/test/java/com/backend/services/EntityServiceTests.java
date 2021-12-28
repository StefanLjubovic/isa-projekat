package com.backend.services;

import com.backend.constants.EntityConstants;
import com.backend.model.*;
import com.backend.repository.IEntityRepository;
import com.backend.service.EntityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.backend.constants.AddressConstants.*;
import static com.backend.constants.EntityConstants.*;
import static com.backend.constants.RegisteredUserConstants.*;
import static com.backend.constants.ReservationConstants.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class )
@SpringBootTest
public class EntityServiceTests {

    @Mock
    private IEntityRepository entityRepository;

    @InjectMocks
    private EntityService entityService;

    @Transactional
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingEntity() throws Throwable{
        Address address=new Address(DB_STREET_NAME,DB_STREET_NUMBER,DB_POSTAL_CODE,DB_CITY,DB_COUNTRY);

        RegisteredUser user = new RegisteredUser(DB_ID,DB_FIRST_NAME,DB_LAST_NAME,DB_PHONE_NUMBER,DB_EMAIL,DB_PASSWORD,DB_USER_STATUS,DB_ENABLED,new Role(DB_ID,DB_ROLE_NAME),DB_DATE,address);
        RegisteredUser user1 = new RegisteredUser(2,DB_FIRST_NAME_SECOND,DB_LAST_NAME_SECOND,DB_PHONE_NUMBER,DB_EMAIL_SECOND,DB_PASSWORD,DB_USER_STATUS,DB_ENABLED,new Role(DB_ID,DB_ROLE_NAME),DB_DATE,address);

        Client client = new Client(user);
        Client secondClient = new Client(user1);

        RentingEntity entity = new RentingEntity(DB_ID,DB_ENTITY_NAME,DB_ENTITY_DESCRIPTION,DB_ENTITY_GRADE,address);

        Reservation reservation = new Reservation(DB_ID,DB_RESERVATION_DATE,DB_RESERVATION_DURATION,DB_MAX_PERSONS,DB_PRICE,client,entity);
        Reservation secondReservation = new Reservation(DB_ID,DB_RESERVATION_DATE,DB_RESERVATION_DURATION,DB_MAX_PERSONS,DB_PRICE,secondClient,entity);

        when(entityRepository.fetchWithPeriods(1)).thenReturn(entity);
        when(entityRepository.save(entity)).thenReturn(entity);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {
            @Override
            public void run() {
                try { Thread.sleep(5000); } catch (InterruptedException e) {}
                Reservation reservationUpdated = entityService.updateUnavailablePeriodTest(reservation);
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Reservation reservationUpdated = entityService.updateUnavailablePeriodTest(secondReservation);
            }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }



    @Test
    public void updateUnavailablePeriod(){
        Address address=new Address(DB_STREET_NAME,DB_STREET_NUMBER,DB_POSTAL_CODE,DB_CITY,DB_COUNTRY);

        RegisteredUser user = new RegisteredUser(DB_ID,DB_FIRST_NAME,DB_LAST_NAME,DB_PHONE_NUMBER,DB_EMAIL,DB_PASSWORD,DB_USER_STATUS,DB_ENABLED,new Role(DB_ID,DB_ROLE_NAME),DB_DATE,address);

        Client client = new Client(user);

        RentingEntity entity = new RentingEntity(DB_ENTITY_NAME,DB_ENTITY_DESCRIPTION,DB_ENTITY_GRADE,address,new HashSet<>());
        entity.getUnavailablePeriods().add(new UnavailablePeriod(DB_ID,DB_UNAVAILABLE_PERIOD_START,DB_UNAVAILABLE_PERIOD_END));

        Reservation reservation = new Reservation(DB_ID,DB_RESERVATION_DATE,DB_RESERVATION_DURATION,DB_MAX_PERSONS,DB_PRICE,client,entity);

        Reservation reservationUpdated = entityService.updateUnavailablePeriod(reservation);
        Assert.isNull(reservationUpdated);
    }

}
