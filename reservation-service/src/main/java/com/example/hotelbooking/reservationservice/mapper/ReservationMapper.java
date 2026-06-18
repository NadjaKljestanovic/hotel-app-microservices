package com.example.hotelbooking.reservationservice.mapper;

import com.example.hotelbooking.reservationservice.dto.ReservationResponse;
import com.example.hotelbooking.reservationservice.entity.Reservation;

public final class ReservationMapper {

    private ReservationMapper() {
    }

    public static ReservationResponse toResponse(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .guestId(reservation.getGuestId())
                .hotelId(reservation.getHotelId())
                .roomId(reservation.getRoomId())
                .checkInDate(reservation.getCheckInDate())
                .checkOutDate(reservation.getCheckOutDate())
                .numberOfGuests(reservation.getNumberOfGuests())
                .totalPrice(reservation.getTotalPrice())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
