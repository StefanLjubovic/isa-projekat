package com.backend.services;

import com.backend.dto.ReservationHistoryDTO;
import com.backend.model.*;
import com.backend.repository.ICottageRepository;
import static com.backend.constants.UnavailaiblePeriodConstants.*;
import com.backend.repository.IReservationRepository;
import com.backend.repository.IUserRepository;
import com.backend.service.CottageOwnerService;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import java.util.List;

import static com.backend.constants.EntityConstants.*;
import static com.backend.constants.ReservationConstants.*;

@RunWith(SpringRunner.class )
@SpringBootTest
public class CottageOwnerServiceTest {

    @Autowired
    private ICottageRepository cottageRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Test
    public void testGetReservationHistoryForCottage(){
        Cottage cottage = cottageRepository.findByName(DB_ENTITY_NAME);
        Reservation reservation = new Reservation(DB_RESERVATION_DATE_TEST, DB_RESERVATION_DURATION, DB_MAX_PERSONS, DB_PRICE, cottage);
        reservationRepository.save(reservation);

        List<ReservationHistoryDTO> result = cottageOwnerService.getReservationHistoryForCottageOwner(cottage.getCottageOwner().getEmail());
        Assert.notEmpty(result);
    }
}
