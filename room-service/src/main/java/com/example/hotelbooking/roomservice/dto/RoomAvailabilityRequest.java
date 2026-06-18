package com.example.hotelbooking.roomservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomAvailabilityRequest {

    @NotNull(message = "Availability flag is required")
    private Boolean available;
}
