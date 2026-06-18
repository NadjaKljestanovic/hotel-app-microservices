package com.example.hotelbooking.paymentservice.event;

import com.example.hotelbooking.common.events.EventConstants;
import com.example.hotelbooking.common.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        rabbitTemplate.convertAndSend(
                EventConstants.PAYMENT_EXCHANGE,
                EventConstants.PAYMENT_COMPLETED_ROUTING_KEY,
                event
        );
    }
}
