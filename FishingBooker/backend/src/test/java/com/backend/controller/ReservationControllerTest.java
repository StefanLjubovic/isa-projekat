package com.backend.controller;

import com.backend.constants.PrincipalClient;
import com.backend.constants.PrincipalCottageOwner;
import com.backend.dto.ReservationDTO;
import com.backend.dto.UnavailablePeriodDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static com.backend.constants.EntityConstants.*;
import static com.backend.constants.EntityConstants.DB_ENTITY_NAME;
import static com.backend.constants.ReservationConstants.*;
import static com.backend.constants.UnavailaiblePeriodConstants.DB_RESERVATION_DATE_TEST;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationControllerTest {
    private static final String URL_PREFIX = "/reservation";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
        @Test
        @WithMockUser(username = "user.fishingbooker+client1@gmail.com", password = "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", roles = "CLIENT")
        public void testGetClientHistoryOfReservations() throws Exception{
            Principal principal = new PrincipalClient();
            String classType = "Cottage";
            mockMvc.perform(get(URL_PREFIX + "/history-reservations/"+classType).principal(principal)).andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*].id").value(hasItem(DB_ENTITY_ID)))
                    .andExpect(jsonPath("$.[*].durationInHours").value(hasItem(DB_RESERVATION_DURATION_HISTORY_TEST)))
                    .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DB_RESERVATION_DATE_HISTORY_TEST)));
        }

        @Test
        @WithMockUser(username = "user.fishingbooker+client1@gmail.com", password = "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", roles = "CLIENT")
        public void testGetClientFutureReservations() throws Exception{
            Principal principal = new PrincipalClient();
            mockMvc.perform(get(URL_PREFIX + "/future-reservations/").principal(principal)).andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*].durationInHours").value(hasItem(DB_RESERVATION_DURATION_HISTORY_TEST)))
                    .andExpect(jsonPath("$.[*].maxPersons").value(hasItem(DB_MAX_PERSONS_FUTURE_RES)))
                    .andExpect(jsonPath("$.[*].entityId").value(hasItem(3)));
        }
}
