package com.example.hotelbooking.roomservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomResponse {

    private Long id;
    private Long hotelId;
    private String roomNumber;
    private String roomType;
    private BigDecimal pricePerNight;
    private Integer capacity;
    private boolean available;
}
