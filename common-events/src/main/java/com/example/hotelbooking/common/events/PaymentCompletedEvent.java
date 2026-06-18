package com.example.hotelbooking.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent {

    private Long paymentId;
    private Long reservationId;
    private Long guestId;
    private BigDecimal amount;
}
