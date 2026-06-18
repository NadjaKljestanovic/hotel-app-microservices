package com.example.hotelbooking.reservationservice.event;

import com.example.hotelbooking.common.events.EventConstants;
import com.example.hotelbooking.common.events.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishReservationCreated(ReservationCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                EventConstants.RESERVATION_EXCHANGE,
                EventConstants.RESERVATION_CREATED_ROUTING_KEY,
                event
        );
    }
}
