package com.example.hotelbooking.hotelservice.service;

import com.example.hotelbooking.hotelservice.dto.HotelRequest;
import com.example.hotelbooking.hotelservice.dto.HotelResponse;
import com.example.hotelbooking.hotelservice.entity.Hotel;
import com.example.hotelbooking.hotelservice.exception.ResourceNotFoundException;
import com.example.hotelbooking.hotelservice.mapper.HotelMapper;
import com.example.hotelbooking.hotelservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;

    @Transactional
    public HotelResponse createHotel(HotelRequest request) {
        Hotel hotel = hotelRepository.save(HotelMapper.toEntity(request));
        return HotelMapper.toResponse(hotel);
    }

    public HotelResponse getHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(HotelMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
    }

    public List<HotelResponse> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(HotelMapper::toResponse)
                .toList();
    }
}
