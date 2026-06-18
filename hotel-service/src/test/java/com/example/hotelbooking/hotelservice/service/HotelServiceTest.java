package com.example.hotelbooking.hotelservice.service;

import com.example.hotelbooking.hotelservice.dto.HotelRequest;
import com.example.hotelbooking.hotelservice.dto.HotelResponse;
import com.example.hotelbooking.hotelservice.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class HotelServiceTest {

    @Autowired
    private HotelService hotelService;

    @Test
    void createHotel_shouldPersistHotel() {
        HotelRequest request = new HotelRequest();
        request.setName("Test Hotel");
        request.setAddress("Test Address");
        request.setCity("Test City");
        request.setCountry("Test Country");
        request.setRating(4.0);

        HotelResponse response = hotelService.createHotel(request);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("Test Hotel");
    }

    @Test
    void getHotelById_whenMissing_shouldThrow() {
        assertThatThrownBy(() -> hotelService.getHotelById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
