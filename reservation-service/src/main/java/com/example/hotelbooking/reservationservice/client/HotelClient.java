package com.example.hotelbooking.reservationservice.client;

import com.example.hotelbooking.reservationservice.dto.HotelClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hotel-service")
public interface HotelClient {

    @GetMapping("/hotels/{id}")
    HotelClientResponse getHotelById(@PathVariable("id") Long id);
}
