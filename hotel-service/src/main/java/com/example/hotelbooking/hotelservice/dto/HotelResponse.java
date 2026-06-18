package com.example.hotelbooking.hotelservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelResponse {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private Double rating;
}
