package com.example.hotelbooking.hotelservice.repository;

import com.example.hotelbooking.hotelservice.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
