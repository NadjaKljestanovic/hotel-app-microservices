package com.example.hotelbooking.paymentservice.service;

import com.example.hotelbooking.common.events.ReservationCreatedEvent;
import com.example.hotelbooking.paymentservice.entity.Payment;
import com.example.hotelbooking.paymentservice.entity.PaymentStatus;
import com.example.hotelbooking.paymentservice.event.PaymentEventPublisher;
import com.example.hotelbooking.paymentservice.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentEventPublisher eventPublisher;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void processReservationPayment_shouldPersistPayment() {
        ReservationCreatedEvent event = ReservationCreatedEvent.builder()
                .reservationId(1L)
                .guestId(2L)
                .totalPrice(new BigDecimal("200.00"))
                .checkInDate(LocalDate.now().plusDays(1))
                .checkOutDate(LocalDate.now().plusDays(3))
                .build();

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            payment.setId(99L);
            return payment;
        });

        paymentService.processReservationPayment(event);

        verify(paymentRepository).save(any(Payment.class));
    }
}
