package com.example.hotelbooking.hotelservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    private Double rating;
}
