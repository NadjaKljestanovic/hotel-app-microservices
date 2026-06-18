package com.example.hotelbooking.hotelservice.controller;

import com.example.hotelbooking.hotelservice.dto.HotelRequest;
import com.example.hotelbooking.hotelservice.dto.HotelResponse;
import com.example.hotelbooking.hotelservice.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse createHotel(@Valid @RequestBody HotelRequest request) {
        return hotelService.createHotel(request);
    }

    @GetMapping("/{id}")
    public HotelResponse getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @GetMapping
    public List<HotelResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }
}
