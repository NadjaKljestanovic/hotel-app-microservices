package com.example.hotelbooking.notificationservice.dto;

import com.example.hotelbooking.notificationservice.entity.NotificationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private Long id;
    private Long guestId;
    private Long reservationId;
    private String message;
    private NotificationStatus status;
    private LocalDateTime createdAt;
}
