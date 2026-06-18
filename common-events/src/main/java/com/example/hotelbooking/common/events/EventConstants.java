package com.example.hotelbooking.common.events;

public final class EventConstants {

    public static final String RESERVATION_EXCHANGE = "reservation.exchange";
    public static final String RESERVATION_CREATED_ROUTING_KEY = "reservation.created";

    public static final String RESERVATION_CREATED_PAYMENT_QUEUE = "reservation.created.payment.queue";
    public static final String RESERVATION_CREATED_NOTIFICATION_QUEUE = "reservation.created.notification.queue";

    public static final String PAYMENT_EXCHANGE = "payment.exchange";
    public static final String PAYMENT_COMPLETED_QUEUE = "payment.completed.queue";
    public static final String PAYMENT_COMPLETED_ROUTING_KEY = "payment.completed";

    private EventConstants() {
    }
}
