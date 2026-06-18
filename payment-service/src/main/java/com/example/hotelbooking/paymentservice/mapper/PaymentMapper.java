package com.example.hotelbooking.paymentservice.mapper;

import com.example.hotelbooking.paymentservice.dto.PaymentResponse;
import com.example.hotelbooking.paymentservice.entity.Payment;

public final class PaymentMapper {

    private PaymentMapper() {
    }

    public static PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .reservationId(payment.getReservationId())
                .guestId(payment.getGuestId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .build();
    }
}
