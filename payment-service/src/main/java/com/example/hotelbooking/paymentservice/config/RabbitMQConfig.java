package com.example.hotelbooking.paymentservice.config;

import com.example.hotelbooking.common.events.EventConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    public Queue reservationCreatedPaymentQueue() {
        return new Queue(EventConstants.RESERVATION_CREATED_PAYMENT_QUEUE, true);
    }

    @Bean
    public Binding reservationCreatedPaymentBinding(Queue reservationCreatedPaymentQueue,
                                                    DirectExchange reservationExchange) {
        return BindingBuilder.bind(reservationCreatedPaymentQueue)
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
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
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
