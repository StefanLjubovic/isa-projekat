package com.backend.controller;

import com.backend.constants.PrincipalCottageOwner;
import com.backend.dto.UnavailablePeriodDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
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

import java.security.Principal;
import static com.backend.constants.EntityConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CottageControllerTests {

    private static final String URL_PREFIX = "/cottage";
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
    void contextLoads() {
    }

    @org.junit.Test
    @WithMockUser(username = "marijakljestan@gmail.com", password = "$2a$10$3kfQZW0qQFJIlfDcadR9UOmPwUDDz4wwkcxxAi1aQmfqZqRxAU/FW", roles = "COTTAGE_OWNER")
    public void testDefineUnavailablePeriod() throws Exception {
        UnavailablePeriodDTO periodDTO = new UnavailablePeriodDTO(DB_UNAVAILABLE_PERIOD_START, DB_UNAVAILABLE_PERIOD_END, DB_ENTITY_ID);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(periodDTO);
        Principal principal = new PrincipalCottageOwner();
        this.mockMvc.perform(post(URL_PREFIX+ "/defineUnavailablePeriod").contentType(contentType).content(json).principal(principal)).andExpect(status().isOk());
    }
}
