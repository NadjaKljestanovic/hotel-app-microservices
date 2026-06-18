package com.example.hotelbooking.notificationservice.service;

import com.example.hotelbooking.common.events.PaymentCompletedEvent;
import com.example.hotelbooking.common.events.ReservationCreatedEvent;
import com.example.hotelbooking.notificationservice.dto.NotificationResponse;
import com.example.hotelbooking.notificationservice.entity.Notification;
import com.example.hotelbooking.notificationservice.entity.NotificationStatus;
import com.example.hotelbooking.notificationservice.exception.ResourceNotFoundException;
import com.example.hotelbooking.notificationservice.mapper.NotificationMapper;
import com.example.hotelbooking.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationResponse getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .map(NotificationMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
    }

    public List<NotificationResponse> getNotificationsByGuestId(Long guestId) {
        return notificationRepository.findByGuestId(guestId).stream()
                .map(NotificationMapper::toResponse)
                .toList();
    }

    @Transactional
    public void handleReservationCreated(ReservationCreatedEvent event) {
        String message = String.format(
                "Your reservation #%d has been created. Check-in: %s, Check-out: %s. Total: %s",
                event.getReservationId(),
                event.getCheckInDate(),
                event.getCheckOutDate(),
                event.getTotalPrice()
        );
        createNotification(event.getGuestId(), event.getReservationId(), message);
    }

    @Transactional
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        String message = String.format(
                "Payment of %s for reservation #%d was completed successfully.",
                event.getAmount(),
                event.getReservationId()
        );
        createNotification(event.getGuestId(), event.getReservationId(), message);
    }

    private void createNotification(Long guestId, Long reservationId, String message) {
        Notification notification = Notification.builder()
                .guestId(guestId)
                .reservationId(reservationId)
                .message(message)
                .status(NotificationStatus.SENT)
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);
        log.info("Notification {} sent to guest {}", saved.getId(), guestId);
    }
}
