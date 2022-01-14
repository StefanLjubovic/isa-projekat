package com.backend.controller;

import com.backend.model.Address;
import com.backend.model.RegistrationRequest;
import com.backend.model.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static com.backend.constants.AddressConstants.*;
import static com.backend.constants.RegisteredUserConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTests {
    private static final String URL_PREFIX = "/auth";
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
    public void testRegisterAdvertiser() throws Exception {
        Address address=new Address(DB_STREET_NAME,DB_STREET_NUMBER,DB_POSTAL_CODE,DB_CITY,DB_COUNTRY);
        RegistrationRequest request = new RegistrationRequest(DB_FIRST_NAME_REGISTER, DB_LAST_NAME_REGISTER, DB_PHONE_NUMBER_REGISTER,
                                        DB_EMAIL_REGISTER, DB_PASSWORD_REGISTER, new Role("ROLE_SHIP_OWNER"), DB_EXPLANATION_REGISTER,
                                        DB_BIOGRAPHY_REGISTER, address);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = mapper.writeValueAsString(request);

        this.mockMvc.perform(post(URL_PREFIX+ "/registerAdvertiser").contentType(contentType).content(json)).andExpect(status().isCreated());
    }
}
