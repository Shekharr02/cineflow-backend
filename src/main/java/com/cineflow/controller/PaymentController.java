package com.cineflow.controller;

import com.cineflow.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<String> pay(@PathVariable Long bookingId){
        return ResponseEntity.ok(paymentService.processPayment(bookingId));
    }
}
