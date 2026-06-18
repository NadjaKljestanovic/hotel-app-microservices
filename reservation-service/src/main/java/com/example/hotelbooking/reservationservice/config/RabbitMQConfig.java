package com.example.hotelbooking.reservationservice.config;

import com.example.hotelbooking.common.events.EventConstants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange reservationExchange() {
        return new DirectExchange(EventConstants.RESERVATION_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
