package com.example.hotelbooking.guestservice.controller;

import com.example.hotelbooking.guestservice.dto.GuestRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class GuestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createGuest_shouldReturn201() throws Exception {
        GuestRequest request = new GuestRequest();
        request.setFirstName("Jane");
        request.setLastName("Doe");
        request.setEmail("jane.doe@example.com");
        request.setDocumentNumber("DOC-200");

        mockMvc.perform(post("/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Jane",
                                  "lastName": "Doe",
                                  "email": "jane.doe@example.com",
                                  "documentNumber": "DOC-200"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    void getGuestById_whenMissing_shouldReturn404() throws Exception {
        mockMvc.perform(get("/guests/999"))
                .andExpect(status().isNotFound());
    }
}
