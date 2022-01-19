package com.backend.services;

import static org.junit.Assert.assertTrue;
import com.backend.model.RentingEntity;
import com.backend.model.Reservation;
import com.backend.model.UnavailablePeriod;
import com.backend.model.*;
import com.backend.repository.IEntityRepository;
import com.backend.repository.IReservationRepository;
import com.backend.service.ReservationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.backend.constants.EntityConstants.*;
import static com.backend.constants.ReservationConstants.*;
import static org.mockito.Mockito.*;
import static com.backend.constants.UnavailaiblePeriodConstants.*;

@RunWith(SpringRunner.class )
@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Mock
    private IReservationRepository reservationRepositoryMock;

    @InjectMocks
    private ReservationService reservationServiceMock;

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingEntity() throws Throwable{

        RentingEntity entity= entityRepository.fetchWithPeriods(2);
        Reservation reservation = new Reservation(DB_RESERVATION_DATE,DB_RESERVATION_DURATION,DB_MAX_PERSONS,DB_PRICE,entity);
        UnavailablePeriod period = new UnavailablePeriod(DB_UNAVAILABLE_PERIOD_START,DB_UNAVAILABLE_PERIOD_END);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {
            @Override
            public void run() {
                try { Thread.sleep(3000); } catch (InterruptedException e) {}
                reservationService.saveTransactionalForTest(reservation);
                return;
            }
        });
        executor.submit(new Runnable() {
            @Override
            public void run() {
                entity.getUnavailablePeriods().add(period);
                entityRepository.save(entity);
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
    public void testIsEntityBookedNow() {

        // 1. Definisanje ponasanja Mock objekta
        when(reservationRepositoryMock.getReservationByRentingEntity_Id(DB_RESERVATION_ID)).thenReturn(Arrays.asList(new Reservation(DB_RESERVATION_DATETIME, DB_RESERVATION_DURATION_IN_HOURS, DB_RESERVATION_MAX_PERSONS, DB_RESERVATION_PRICE)));

        // 2. Akcija
        Boolean isEntityBookedNow = reservationServiceMock.isEntityBookedNow(DB_RESERVATION_ID);

        // 3. Verifikacija
        assertTrue(isEntityBookedNow);
        verify(reservationRepositoryMock, times(1)).getReservationByRentingEntity_Id(DB_RESERVATION_ID);
        verifyNoMoreInteractions(reservationRepositoryMock);

    }

    @Test
    public void testIsEntityBooked() {
        Reservation reservation = new Reservation(DB_RESERVATION_DATE_TEST,DB_RESERVATION_DURATION,DB_MAX_PERSONS,DB_PRICE);
        Assert.assertFalse(reservationService.isEntityBooked(reservation));
    }
}
