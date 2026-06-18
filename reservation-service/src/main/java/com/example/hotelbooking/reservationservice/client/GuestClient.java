package com.example.hotelbooking.reservationservice.client;

import com.example.hotelbooking.reservationservice.dto.GuestClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "guest-service")
public interface GuestClient {

    @GetMapping("/guests/{id}")
    GuestClientResponse getGuestById(@PathVariable("id") Long id);
}
