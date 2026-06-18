package com.example.hotelbooking.notificationservice.config;

import com.example.hotelbooking.common.events.EventConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
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
    public DirectExchange paymentExchange() {
        return new DirectExchange(EventConstants.PAYMENT_EXCHANGE);
    }

    @Bean
    public Queue reservationCreatedNotificationQueue() {
        return new Queue(EventConstants.RESERVATION_CREATED_NOTIFICATION_QUEUE, true);
    }

    @Bean
    public Binding reservationCreatedNotificationBinding(Queue reservationCreatedNotificationQueue,
                                                         DirectExchange reservationExchange) {
        return BindingBuilder.bind(reservationCreatedNotificationQueue)
                .to(reservationExchange)
                .with(EventConstants.RESERVATION_CREATED_ROUTING_KEY);
    }

    @Bean
    public Queue paymentCompletedQueue() {
        return new Queue(EventConstants.PAYMENT_COMPLETED_QUEUE, true);
    }

    @Bean
    public Binding paymentCompletedBinding(Queue paymentCompletedQueue, DirectExchange paymentExchange) {
        return BindingBuilder.bind(paymentCompletedQueue)
                .to(paymentExchange)
                .with(EventConstants.PAYMENT_COMPLETED_ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
