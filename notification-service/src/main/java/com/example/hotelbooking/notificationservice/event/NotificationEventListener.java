package com.example.hotelbooking.notificationservice.event;

import com.example.hotelbooking.common.events.EventConstants;
import com.example.hotelbooking.common.events.PaymentCompletedEvent;
import com.example.hotelbooking.common.events.ReservationCreatedEvent;
import com.example.hotelbooking.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = EventConstants.RESERVATION_CREATED_NOTIFICATION_QUEUE)
    public void onReservationCreated(ReservationCreatedEvent event) {
        notificationService.handleReservationCreated(event);
    }

    @RabbitListener(queues = EventConstants.PAYMENT_COMPLETED_QUEUE)
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        notificationService.handlePaymentCompleted(event);
    }
}
