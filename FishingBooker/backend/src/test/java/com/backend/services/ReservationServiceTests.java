package com.backend.services;

import com.backend.model.RentingEntity;
import com.backend.model.Reservation;
import com.backend.model.UnavailablePeriod;
import com.backend.repository.IEntityRepository;
import com.backend.service.ReservationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.backend.constants.EntityConstants.DB_UNAVAILABLE_PERIOD_END;
import static com.backend.constants.EntityConstants.DB_UNAVAILABLE_PERIOD_START;
import static com.backend.constants.ReservationConstants.*;
import static com.backend.constants.ReservationConstants.DB_PRICE;

@RunWith(SpringRunner.class )
@SpringBootTest
public class ReservationServiceTests {

    @Autowired
    private IEntityRepository entityRepository;

    @Autowired
    private ReservationService reservationService;

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingEntity() throws Throwable{

        RentingEntity entity=entityRepository.fetchWithPeriods(2);
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
}
