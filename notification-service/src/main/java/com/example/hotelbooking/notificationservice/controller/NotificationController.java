package com.example.hotelbooking.notificationservice.controller;

import com.example.hotelbooking.notificationservice.dto.NotificationResponse;
import com.example.hotelbooking.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{id}")
    public NotificationResponse getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/guest/{guestId}")
    public List<NotificationResponse> getNotificationsByGuestId(@PathVariable Long guestId) {
        return notificationService.getNotificationsByGuestId(guestId);
    }
}
