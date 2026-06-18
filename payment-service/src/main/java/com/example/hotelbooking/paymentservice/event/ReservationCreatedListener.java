package com.example.hotelbooking.paymentservice.event;

import com.example.hotelbooking.common.events.EventConstants;
import com.example.hotelbooking.common.events.ReservationCreatedEvent;
import com.example.hotelbooking.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationCreatedListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = EventConstants.RESERVATION_CREATED_PAYMENT_QUEUE)
    public void handleReservationCreated(ReservationCreatedEvent event) {
        paymentService.processReservationPayment(event);
    }
}
