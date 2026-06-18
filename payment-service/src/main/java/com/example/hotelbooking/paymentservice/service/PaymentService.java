package com.example.hotelbooking.paymentservice.service;

import com.example.hotelbooking.common.events.PaymentCompletedEvent;
import com.example.hotelbooking.common.events.ReservationCreatedEvent;
import com.example.hotelbooking.paymentservice.dto.PaymentResponse;
import com.example.hotelbooking.paymentservice.entity.Payment;
import com.example.hotelbooking.paymentservice.entity.PaymentStatus;
import com.example.hotelbooking.paymentservice.event.PaymentEventPublisher;
import com.example.hotelbooking.paymentservice.exception.ResourceNotFoundException;
import com.example.hotelbooking.paymentservice.mapper.PaymentMapper;
import com.example.hotelbooking.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private static final Random RANDOM = new Random();

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    public PaymentResponse getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(PaymentMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    public List<PaymentResponse> getPaymentsByReservationId(Long reservationId) {
        return paymentRepository.findByReservationId(reservationId).stream()
                .map(PaymentMapper::toResponse)
                .toList();
    }

    @Transactional
    public void processReservationPayment(ReservationCreatedEvent event) {
        log.info("Processing payment for reservation {}", event.getReservationId());

        // Simulate payment processing: 90% success rate
        PaymentStatus status = RANDOM.nextInt(10) < 9 ? PaymentStatus.COMPLETED : PaymentStatus.FAILED;

        Payment payment = Payment.builder()
                .reservationId(event.getReservationId())
                .guestId(event.getGuestId())
                .amount(event.getTotalPrice())
                .status(status)
                .paymentDate(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);
        log.info("Payment {} processed with status {}", saved.getId(), saved.getStatus());

        if (status == PaymentStatus.COMPLETED) {
            eventPublisher.publishPaymentCompleted(PaymentCompletedEvent.builder()
                    .paymentId(saved.getId())
                    .reservationId(saved.getReservationId())
                    .guestId(saved.getGuestId())
                    .amount(saved.getAmount())
                    .build());
        }
    }
}
