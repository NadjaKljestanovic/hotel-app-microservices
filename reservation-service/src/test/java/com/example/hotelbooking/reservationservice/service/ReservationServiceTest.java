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
import com.example.hotelbooking.reservationservice.event.ReservationEventPublisher;
import com.example.hotelbooking.reservationservice.exception.BusinessValidationException;
import com.example.hotelbooking.reservationservice.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private GuestClient guestClient;

    @Mock
    private HotelClient hotelClient;

    @Mock
    private RoomClient roomClient;

    @Mock
    private ReservationEventPublisher eventPublisher;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void createReservation_shouldValidateRemoteServicesAndPublishEvent() {
        ReservationRequest request = new ReservationRequest();
        request.setGuestId(1L);
        request.setHotelId(1L);
        request.setRoomId(1L);
        request.setCheckInDate(LocalDate.now().plusDays(10));
        request.setCheckOutDate(LocalDate.now().plusDays(13));
        request.setNumberOfGuests(2);

        when(guestClient.getGuestById(1L)).thenReturn(GuestClientResponse.builder().id(1L).build());
        when(hotelClient.getHotelById(1L)).thenReturn(HotelClientResponse.builder().id(1L).build());
        when(roomClient.getRoomById(1L)).thenReturn(RoomClientResponse.builder()
                .id(1L)
                .hotelId(1L)
                .pricePerNight(new BigDecimal("100.00"))
                .available(true)
                .build());

        Reservation saved = Reservation.builder()
                .id(10L)
                .guestId(1L)
                .hotelId(1L)
                .roomId(1L)
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .numberOfGuests(2)
                .totalPrice(new BigDecimal("300.00"))
                .status(ReservationStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        when(reservationRepository.save(any(Reservation.class))).thenReturn(saved);

        ReservationResponse response = reservationService.createReservation(request);

        assertThat(response.getTotalPrice()).isEqualByComparingTo("300.00");
        assertThat(response.getStatus()).isEqualTo(ReservationStatus.CREATED);
        verify(eventPublisher).publishReservationCreated(any(ReservationCreatedEvent.class));
    }

    @Test
    void createReservation_whenRoomUnavailable_shouldThrow() {
        ReservationRequest request = new ReservationRequest();
        request.setGuestId(1L);
        request.setHotelId(1L);
        request.setRoomId(1L);
        request.setCheckInDate(LocalDate.now().plusDays(5));
        request.setCheckOutDate(LocalDate.now().plusDays(7));
        request.setNumberOfGuests(2);

        when(guestClient.getGuestById(1L)).thenReturn(GuestClientResponse.builder().id(1L).build());
        when(hotelClient.getHotelById(1L)).thenReturn(HotelClientResponse.builder().id(1L).build());
        when(roomClient.getRoomById(1L)).thenReturn(RoomClientResponse.builder()
                .id(1L)
                .hotelId(1L)
                .available(false)
                .build());

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(BusinessValidationException.class)
                .hasMessageContaining("not available");
    }
}
