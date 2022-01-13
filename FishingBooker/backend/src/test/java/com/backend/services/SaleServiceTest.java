package com.backend.services;

import static org.junit.Assert.assertFalse;

import com.backend.model.RentingEntity;
import com.backend.model.Reservation;
import com.backend.model.Sale;
import com.backend.repository.IReservationRepository;
import com.backend.service.SaleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static com.backend.constants.EntityConstants.DB_ENTITY_ID;
import static com.backend.constants.SaleConstants.*;
import static com.backend.constants.ReservationConstants.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class )
@SpringBootTest
public class SaleServiceTest {

    @Mock
    private IReservationRepository reservationRepositoryMock;

    @InjectMocks
    private SaleService saleService;

    @Test
    public void testIfSaleOverlapsWithExistingReservation() {

        // 1. Definisanje ponasanja Mock objekta
        Sale sale = new Sale(DB_SALE_DATETIME_FROM, DB_SALE_DURATION_IN_HOURS, DB_SALE_MAXIMUM_PERSONS, DB_SALE_EXPIRE_DATETIME, DB_SALE_ADDITIONAL_SERVICES, DB_SALE_PRICE);
        Reservation reservation = new Reservation(DB_RESERVATION_DATETIME, DB_RESERVATION_DURATION_IN_HOURS, DB_RESERVATION_MAX_PERSONS, DB_RESERVATION_PRICE);
        RentingEntity entity = new RentingEntity();
        entity.setId(DB_ENTITY_ID);

        when(reservationRepositoryMock.fetchByEntityId(DB_ENTITY_ID)).thenReturn(Arrays.asList(reservation));

        // 2. Akcija
        boolean overlapsWithExistingReservation = saleService.overlapsWithExistingReservation(sale, entity);

        // 3. Verifikacija
        assertFalse(overlapsWithExistingReservation);
        verify(reservationRepositoryMock, times(1)).fetchByEntityId(DB_ENTITY_ID);

    }
}
