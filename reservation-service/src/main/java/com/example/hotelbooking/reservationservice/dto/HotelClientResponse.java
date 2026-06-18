package com.example.hotelbooking.reservationservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelClientResponse {

    private Long id;
    private String name;
    private String city;
}
