package com.example.hotelbooking.guestservice.repository;

import com.example.hotelbooking.guestservice.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}
