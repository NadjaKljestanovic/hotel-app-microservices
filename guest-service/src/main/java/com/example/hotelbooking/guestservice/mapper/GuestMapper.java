package com.example.hotelbooking.guestservice.mapper;

import com.example.hotelbooking.guestservice.dto.GuestRequest;
import com.example.hotelbooking.guestservice.dto.GuestResponse;
import com.example.hotelbooking.guestservice.entity.Guest;

public final class GuestMapper {

    private GuestMapper() {
    }

    public static Guest toEntity(GuestRequest request) {
        return Guest.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .documentNumber(request.getDocumentNumber())
                .build();
    }

    public static GuestResponse toResponse(Guest guest) {
        return GuestResponse.builder()
                .id(guest.getId())
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .email(guest.getEmail())
                .phoneNumber(guest.getPhoneNumber())
                .documentNumber(guest.getDocumentNumber())
                .build();
    }
}
