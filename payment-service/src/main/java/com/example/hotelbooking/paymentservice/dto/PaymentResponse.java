package com.example.hotelbooking.paymentservice.dto;

import com.example.hotelbooking.paymentservice.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {

    private Long id;
    private Long reservationId;
    private Long guestId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
}
