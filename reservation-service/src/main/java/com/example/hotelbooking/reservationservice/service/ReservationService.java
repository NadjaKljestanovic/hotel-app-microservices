package com.example.hotelbooking.reservationservice.service;

import com.example.hotelbooking.common.events.ReservationCreatedEvent;
import com.example.hotelbooking.reservationservice.client.GuestClient;
import com.example.hotelbooking.reservationservice.client.HotelClient;
import com.example.hotelbooking.reservationservice.client.RoomClient;
import com.example.hotelbooking.reservationservice.dto.GuestClientResponse;
import com.example.hotelbooking.reservationservice.dto.HotelClientResponse;
import com.example.hotelbooking.reservationservice.dto.ReservationRequest;
import com.example.hotelbooking.reservationservice.dto.ReservationResponse;
import com.example.hotelbooking.reservationservice.dto.RoomClientResponse;
import com.example.hotelbooking.reservationservice.entity.Reservation;
import com.example.hotelbooking.reservationservice.entity.ReservationStatus;
import com.example.hotelbooking.reservationservice.exception.BusinessValidationException;
import com.example.hotelbooking.reservationservice.exception.ResourceNotFoundException;
import com.example.hotelbooking.reservationservice.event.ReservationEventPublisher;
import com.example.hotelbooking.reservationservice.mapper.ReservationMapper;
import com.example.hotelbooking.reservationservice.repository.ReservationRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestClient guestClient;
    private final HotelClient hotelClient;
    private final RoomClient roomClient;
    private final ReservationEventPublisher eventPublisher;

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        validateDates(request.getCheckInDate(), request.getCheckOutDate());

        GuestClientResponse guest = fetchGuest(request.getGuestId());
        HotelClientResponse hotel = fetchHotel(request.getHotelId());
        RoomClientResponse room = fetchRoom(request.getRoomId());
        validateRoom(room, request);

        BigDecimal totalPrice = calculateTotalPrice(room.getPricePerNight(),
                request.getCheckInDate(), request.getCheckOutDate());

        Reservation reservation = Reservation.builder()
                .guestId(request.getGuestId())
                .hotelId(request.getHotelId())
                .roomId(request.getRoomId())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .numberOfGuests(request.getNumberOfGuests())
                .totalPrice(totalPrice)
                .status(ReservationStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        Reservation saved = reservationRepository.save(reservation);

        eventPublisher.publishReservationCreated(ReservationCreatedEvent.builder()
                .reservationId(saved.getId())
                .guestId(saved.getGuestId())
                .hotelId(saved.getHotelId())
                .roomId(saved.getRoomId())
                .totalPrice(saved.getTotalPrice())
                .checkInDate(saved.getCheckInDate())
                .checkOutDate(saved.getCheckOutDate())
                .build());

        return ReservationMapper.toResponse(saved);
    }

    public ReservationResponse getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(ReservationMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toResponse)
                .toList();
    }

    @Transactional
    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new BusinessValidationException("Reservation is already cancelled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return ReservationMapper.toResponse(reservationRepository.save(reservation));
    }

    private void validateDates(LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            throw new BusinessValidationException("Check-out date must be after check-in date");
        }
    }

    private void validateRoom(RoomClientResponse room, ReservationRequest request) {
        if (room == null || room.getId() == null) {
            throw new BusinessValidationException("Room not found with id: " + request.getRoomId());
        }
        if (!room.isAvailable()) {
            throw new BusinessValidationException("Room is not available");
        }
        if (!room.getHotelId().equals(request.getHotelId())) {
            throw new BusinessValidationException("Room does not belong to the specified hotel");
        }
    }

    private BigDecimal calculateTotalPrice(BigDecimal pricePerNight, LocalDate checkIn, LocalDate checkOut) {
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        return pricePerNight.multiply(BigDecimal.valueOf(nights));
    }

    private GuestClientResponse fetchGuest(Long guestId) {
        try {
            return guestClient.getGuestById(guestId);
        } catch (FeignException.NotFound e) {
            throw new BusinessValidationException("Guest not found with id: " + guestId);
        }
    }

    private HotelClientResponse fetchHotel(Long hotelId) {
        try {
            return hotelClient.getHotelById(hotelId);
        } catch (FeignException.NotFound e) {
            throw new BusinessValidationException("Hotel not found with id: " + hotelId);
        }
    }

    private RoomClientResponse fetchRoom(Long roomId) {
        try {
            return roomClient.getRoomById(roomId);
        } catch (FeignException.NotFound e) {
            throw new BusinessValidationException("Room not found with id: " + roomId);
        }
    }
}
