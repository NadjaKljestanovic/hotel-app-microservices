package com.example.hotelbooking.notificationservice.service;

import com.example.hotelbooking.common.events.ReservationCreatedEvent;
import com.example.hotelbooking.notificationservice.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void handleReservationCreated_shouldCreateNotification() {
        ReservationCreatedEvent event = ReservationCreatedEvent.builder()
                .reservationId(5L)
                .guestId(3L)
                .hotelId(1L)
                .roomId(2L)
                .totalPrice(new BigDecimal("450.00"))
                .checkInDate(LocalDate.now().plusDays(10))
                .checkOutDate(LocalDate.now().plusDays(13))
                .build();

        notificationService.handleReservationCreated(event);

        assertThat(notificationRepository.findByGuestId(3L)).hasSize(1);
    }
}
