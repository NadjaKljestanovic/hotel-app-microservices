package com.example.hotelbooking.paymentservice.controller;

import com.example.hotelbooking.paymentservice.dto.PaymentResponse;
import com.example.hotelbooking.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public PaymentResponse getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping("/reservation/{reservationId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<PaymentResponse> getPaymentsByReservationId(@PathVariable Long reservationId) {
        return paymentService.getPaymentsByReservationId(reservationId);
    }
}
