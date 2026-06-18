package com.example.hotelbooking.reservationservice.controller;

import com.example.hotelbooking.reservationservice.client.GuestClient;
import com.example.hotelbooking.reservationservice.client.HotelClient;
import com.example.hotelbooking.reservationservice.client.RoomClient;
import com.example.hotelbooking.reservationservice.dto.GuestClientResponse;
import com.example.hotelbooking.reservationservice.dto.HotelClientResponse;
import com.example.hotelbooking.reservationservice.dto.RoomClientResponse;
import com.example.hotelbooking.reservationservice.event.ReservationEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestClient guestClient;

    @MockBean
    private HotelClient hotelClient;

    @MockBean
    private RoomClient roomClient;

    @MockBean
    private ReservationEventPublisher eventPublisher;

    @Test
    void createReservation_shouldReturn201() throws Exception {
        when(guestClient.getGuestById(anyLong())).thenReturn(GuestClientResponse.builder().id(1L).build());
        when(hotelClient.getHotelById(anyLong())).thenReturn(HotelClientResponse.builder().id(1L).build());
        when(roomClient.getRoomById(anyLong())).thenReturn(RoomClientResponse.builder()
                .id(1L)
                .hotelId(1L)
                .pricePerNight(new BigDecimal("120.00"))
                .available(true)
                .build());

        LocalDate checkIn = LocalDate.now().plusDays(30);
        LocalDate checkOut = LocalDate.now().plusDays(33);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "guestId": 1,
                                  "hotelId": 1,
                                  "roomId": 1,
                                  "checkInDate": "%s",
                                  "checkOutDate": "%s",
                                  "numberOfGuests": 2
                                }
                                """.formatted(checkIn, checkOut)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice").value(360.0));
    }
}
