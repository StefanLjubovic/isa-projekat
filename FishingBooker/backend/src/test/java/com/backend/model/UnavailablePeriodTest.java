package com.backend.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.backend.constants.ReservationConstants.*;
import static com.backend.constants.ReservationConstants.DB_PRICE;
import static com.backend.constants.UnavailaiblePeriodConstants.*;
import static com.backend.constants.RegisteredUserConstants.DB_ID;

@RunWith(SpringRunner.class )
@SpringBootTest
public class UnavailablePeriodTest {

    @Test
    public void testCheckIfOverlapsWithExistingUnavailablePeriod(){
        Set<UnavailablePeriod> periods = new HashSet<>();
        periods.add(new UnavailablePeriod(DB_ID, DB_DATE_BEGIN, DB_DATE_END));

        UnavailablePeriod periodToCheck = new UnavailablePeriod(2, DB_DATE_BEGIN_TEST, DB_DATE_END_TEST);
        Assert.assertTrue(periodToCheck.overlapsWithExistingUnavailablePeriods(periods));
    }

    @Test
    public void testCheckIfOverlapsWithExistingReservations(){
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation(DB_RESERVATION_DATE_TEST,DB_RESERVATION_DURATION,DB_MAX_PERSONS, DB_PRICE));

        UnavailablePeriod periodToCheck = new UnavailablePeriod(2, DB_DATE_BEGIN_TEST, DB_DATE_END_TEST);
        Assert.assertFalse(periodToCheck.overlapsWithExistingReservations(reservations));
    }

    @Test
    public void testCheckIfOverlapsWithExistingSales(){
        Set<Sale> sales = new HashSet<>();
        sales.add(new Sale(DB_SALE_DATE_TEST, DB_RESERVATION_DURATION, DB_MAX_PERSONS, DB_PRICE));

        UnavailablePeriod periodToCheck = new UnavailablePeriod(2, DB_DATE_BEGIN_TEST, DB_DATE_END_TEST);
        Assert.assertFalse(periodToCheck.overlapsWithExistingSales(sales));
    }
}
