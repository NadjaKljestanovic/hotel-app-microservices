package com.example.hotelbooking.reservationservice.client;

import com.example.hotelbooking.reservationservice.dto.RoomClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "room-service")
public interface RoomClient {

    @GetMapping("/rooms/{id}")
    RoomClientResponse getRoomById(@PathVariable("id") Long id);
}
