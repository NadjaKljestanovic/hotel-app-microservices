package com.example.hotelbooking.roomservice.controller;

import com.example.hotelbooking.roomservice.dto.RoomAvailabilityRequest;
import com.example.hotelbooking.roomservice.dto.RoomRequest;
import com.example.hotelbooking.roomservice.dto.RoomResponse;
import com.example.hotelbooking.roomservice.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse createRoom(@Valid @RequestBody RoomRequest request) {
        return roomService.createRoom(request);
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    @GetMapping
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponse> getRoomsByHotelId(@PathVariable Long hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }

    @PatchMapping("/{id}/availability")
    public RoomResponse updateAvailability(@PathVariable Long id,
                                           @Valid @RequestBody RoomAvailabilityRequest request) {
        return roomService.updateAvailability(id, request);
    }
}
