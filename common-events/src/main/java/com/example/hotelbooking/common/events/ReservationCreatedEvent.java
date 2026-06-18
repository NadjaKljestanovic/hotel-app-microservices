package com.example.hotelbooking.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreatedEvent {

    private Long reservationId;
    private Long guestId;
    private Long hotelId;
    private Long roomId;
    private BigDecimal totalPrice;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
