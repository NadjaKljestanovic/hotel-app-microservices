package com.example.hotelbooking.roomservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class RoomControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createRoom_shouldReturn201() throws Exception {
        mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "hotelId": 1,
                                  "roomNumber": "305",
                                  "roomType": "SUITE",
                                  "pricePerNight": 250.00,
                                  "capacity": 4,
                                  "available": true
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomNumber").value("305"));
    }
}
