package com.example.hotelbooking.reservationservice.dto;

import com.example.hotelbooking.reservationservice.entity.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ReservationResponse {

    private Long id;
    private Long guestId;
    private Long hotelId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private BigDecimal totalPrice;
    private ReservationStatus status;
    private LocalDateTime createdAt;
}
