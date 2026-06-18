package com.example.hotelbooking.guestservice.config;

import com.example.hotelbooking.guestservice.entity.Guest;
import com.example.hotelbooking.guestservice.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final GuestRepository guestRepository;

    @Bean
    @Profile("!test")
    CommandLineRunner initGuests() {
        return args -> {
            if (guestRepository.count() == 0) {
                guestRepository.save(Guest.builder()
                        .firstName("Anna")
                        .lastName("Smith")
                        .email("anna.smith@example.com")
                        .phoneNumber("+1234567890")
                        .documentNumber("DOC-001")
                        .build());
            }
        };
    }
}
