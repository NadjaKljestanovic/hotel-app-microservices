package com.example.hotelbooking.hotelservice.mapper;

import com.example.hotelbooking.hotelservice.dto.HotelRequest;
import com.example.hotelbooking.hotelservice.dto.HotelResponse;
import com.example.hotelbooking.hotelservice.entity.Hotel;

public final class HotelMapper {

    private HotelMapper() {
    }

    public static Hotel toEntity(HotelRequest request) {
        return Hotel.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .rating(request.getRating())
                .build();
    }

    public static HotelResponse toResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .country(hotel.getCountry())
                .rating(hotel.getRating())
                .build();
    }
}
