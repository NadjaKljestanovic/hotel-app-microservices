package com.example.hotelbooking.reservationservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomClientResponse {

    private Long id;
    private Long hotelId;
    private String roomNumber;
    private BigDecimal pricePerNight;
    private boolean available;
}
