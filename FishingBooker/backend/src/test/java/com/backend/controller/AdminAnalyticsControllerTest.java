package com.backend.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.backend.constants.AdminAnalyticsConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AdminAnalyticsControllerTest {

    private static final String URL_PREFIX = "/adminAnalytics";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = "user.fishingbooker+admin@gmail.com", password = "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", roles = "ADMIN")
    public void testGetIncomePercentage() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/percentage")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.key").value(DB_SP_KEY))
                .andExpect(jsonPath("$.value").value(DB_SP_VALUE));
    }

    @Test
    @WithMockUser(username = "user.fishingbooker+admin@gmail.com", password = "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", roles = "ADMIN")
    public void testGetAllReservationIncomes() throws Exception {
        mockMvc.perform(get(URL_PREFIX + "/income")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.[*].entityName").value(hasItem(DB_INCOME_ENTITY_NAME)))
                .andExpect(jsonPath("$.[*].clientEmail").value(hasItem(DB_INCOME_CLIENT_EMAIL)))
                .andExpect(jsonPath("$.[*].income").value(hasItem(DB_INCOME_INCOME)));
    }
}
