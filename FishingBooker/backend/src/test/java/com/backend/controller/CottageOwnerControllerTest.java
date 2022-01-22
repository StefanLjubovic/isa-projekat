package com.backend.controller;

import com.backend.constants.PrincipalCottageOwner;
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

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CottageOwnerControllerTest {

    private static final String URL_PREFIX = "/cottageOwner";
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
    @WithMockUser(username = "user.fishingbooker+cottage@gmail.com", password = "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", roles = "COTTAGE_OWNER")
    public void testGetReservationHistory() throws Exception{
        Principal principal = new PrincipalCottageOwner();
        mockMvc.perform(get(URL_PREFIX + "/reservation-history").principal(principal)).andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(hasItem(DB_ENTITY_ID)))
                .andExpect(jsonPath("$.[*].entityName").value(hasItem(DB_ENTITY_NAME)))
                .andExpect(jsonPath("$.[*].entityId").value(hasItem(DB_ENTITY_ID)));
    }
}
