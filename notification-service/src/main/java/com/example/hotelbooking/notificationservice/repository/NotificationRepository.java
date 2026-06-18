package com.example.hotelbooking.notificationservice.repository;

import com.example.hotelbooking.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByGuestId(Long guestId);
}
