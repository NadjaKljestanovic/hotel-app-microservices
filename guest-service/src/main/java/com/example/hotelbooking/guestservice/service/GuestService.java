package com.example.hotelbooking.guestservice.service;

import com.example.hotelbooking.guestservice.dto.GuestRequest;
import com.example.hotelbooking.guestservice.dto.GuestResponse;
import com.example.hotelbooking.guestservice.entity.Guest;
import com.example.hotelbooking.guestservice.exception.ResourceNotFoundException;
import com.example.hotelbooking.guestservice.mapper.GuestMapper;
import com.example.hotelbooking.guestservice.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuestService {

    private final GuestRepository guestRepository;

    @Transactional
    public GuestResponse createGuest(GuestRequest request) {
        Guest guest = guestRepository.save(GuestMapper.toEntity(request));
        return GuestMapper.toResponse(guest);
    }

    public GuestResponse getGuestById(Long id) {
        return guestRepository.findById(id)
                .map(GuestMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id: " + id));
    }

    public List<GuestResponse> getAllGuests() {
        return guestRepository.findAll().stream()
                .map(GuestMapper::toResponse)
                .toList();
    }
}
