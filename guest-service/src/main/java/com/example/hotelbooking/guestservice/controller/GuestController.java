package com.example.hotelbooking.guestservice.controller;

import com.example.hotelbooking.guestservice.dto.GuestRequest;
import com.example.hotelbooking.guestservice.dto.GuestResponse;
import com.example.hotelbooking.guestservice.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public GuestResponse createGuest(@Valid @RequestBody GuestRequest request) {
        return guestService.createGuest(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GuestResponse getGuestById(@PathVariable Long id) {
        return guestService.getGuestById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<GuestResponse> getAllGuests() {
        return guestService.getAllGuests();
    }
}
