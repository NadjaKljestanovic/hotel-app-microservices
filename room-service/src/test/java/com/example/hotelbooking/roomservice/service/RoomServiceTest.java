package com.example.hotelbooking.roomservice.service;

import com.example.hotelbooking.roomservice.dto.RoomAvailabilityRequest;
import com.example.hotelbooking.roomservice.dto.RoomRequest;
import com.example.hotelbooking.roomservice.dto.RoomResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Test
    void createRoom_andUpdateAvailability() {
        RoomRequest request = new RoomRequest();
        request.setHotelId(1L);
        request.setRoomNumber("201");
        request.setRoomType("STANDARD");
        request.setPricePerNight(new BigDecimal("99.99"));
        request.setCapacity(2);
        request.setAvailable(true);

        RoomResponse created = roomService.createRoom(request);
        assertThat(created.isAvailable()).isTrue();

        RoomAvailabilityRequest availabilityRequest = new RoomAvailabilityRequest();
        availabilityRequest.setAvailable(false);

        RoomResponse updated = roomService.updateAvailability(created.getId(), availabilityRequest);
        assertThat(updated.isAvailable()).isFalse();
    }
}
