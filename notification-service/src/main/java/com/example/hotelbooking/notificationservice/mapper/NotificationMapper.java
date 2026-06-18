package com.example.hotelbooking.notificationservice.mapper;

import com.example.hotelbooking.notificationservice.dto.NotificationResponse;
import com.example.hotelbooking.notificationservice.entity.Notification;

public final class NotificationMapper {

    private NotificationMapper() {
    }

    public static NotificationResponse toResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .guestId(notification.getGuestId())
                .reservationId(notification.getReservationId())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
