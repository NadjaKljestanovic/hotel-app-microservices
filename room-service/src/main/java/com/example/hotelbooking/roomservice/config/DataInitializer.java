package com.example.hotelbooking.roomservice.config;

import com.example.hotelbooking.roomservice.entity.Room;
import com.example.hotelbooking.roomservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RoomRepository roomRepository;

    @Bean
    @Profile("!test")
    CommandLineRunner initRooms() {
        return args -> {
            if (roomRepository.count() == 0) {
                roomRepository.save(Room.builder()
                        .hotelId(1L)
                        .roomNumber("101")
                        .roomType("DELUXE")
                        .pricePerNight(new BigDecimal("150.00"))
                        .capacity(2)
                        .available(true)
                        .build());
            }
        };
    }
}
