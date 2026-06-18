package com.example.hotelbooking.roomservice.mapper;

import com.example.hotelbooking.roomservice.dto.RoomRequest;
import com.example.hotelbooking.roomservice.dto.RoomResponse;
import com.example.hotelbooking.roomservice.entity.Room;

public final class RoomMapper {

    private RoomMapper() {
    }

    public static Room toEntity(RoomRequest request) {
        return Room.builder()
                .hotelId(request.getHotelId())
                .roomNumber(request.getRoomNumber())
                .roomType(request.getRoomType())
                .pricePerNight(request.getPricePerNight())
                .capacity(request.getCapacity())
                .available(request.isAvailable())
                .build();
    }

    public static RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .hotelId(room.getHotelId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .pricePerNight(room.getPricePerNight())
                .capacity(room.getCapacity())
                .available(room.isAvailable())
                .build();
    }
}
