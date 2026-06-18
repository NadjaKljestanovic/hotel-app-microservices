package com.example.hotelbooking.reservationservice.repository;

import com.example.hotelbooking.reservationservice.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
