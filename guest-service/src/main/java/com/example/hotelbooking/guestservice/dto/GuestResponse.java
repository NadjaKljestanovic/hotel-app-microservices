package com.example.hotelbooking.guestservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuestResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String documentNumber;
}
