package com.example.hotelbooking.roomservice.service;

import com.example.hotelbooking.roomservice.dto.RoomAvailabilityRequest;
import com.example.hotelbooking.roomservice.dto.RoomRequest;
import com.example.hotelbooking.roomservice.dto.RoomResponse;
import com.example.hotelbooking.roomservice.entity.Room;
import com.example.hotelbooking.roomservice.exception.ResourceNotFoundException;
import com.example.hotelbooking.roomservice.mapper.RoomMapper;
import com.example.hotelbooking.roomservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    @Transactional
    public RoomResponse createRoom(RoomRequest request) {
        Room room = roomRepository.save(RoomMapper.toEntity(request));
        return RoomMapper.toResponse(room);
    }

    public RoomResponse getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(RoomMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::toResponse)
                .toList();
    }

    public List<RoomResponse> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId).stream()
                .map(RoomMapper::toResponse)
                .toList();
    }

    @Transactional
    public RoomResponse updateAvailability(Long id, RoomAvailabilityRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        room.setAvailable(request.getAvailable());
        return RoomMapper.toResponse(roomRepository.save(room));
    }
}
