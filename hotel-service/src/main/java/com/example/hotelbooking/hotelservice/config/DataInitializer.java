package com.example.hotelbooking.hotelservice.config;

import com.example.hotelbooking.hotelservice.entity.Hotel;
import com.example.hotelbooking.hotelservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final HotelRepository hotelRepository;

    @Bean
    @Profile("!test")
    CommandLineRunner initHotels() {
        return args -> {
            if (hotelRepository.count() == 0) {
                hotelRepository.save(Hotel.builder()
                        .name("Grand Plaza Hotel")
                        .address("123 Main Street")
                        .city("New York")
                        .country("USA")
                        .rating(4.5)
                        .build());
            }
        };
    }
}
