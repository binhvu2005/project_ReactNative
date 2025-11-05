package com.example.backend_booking.controller;

import com.example.backend_booking.dto.PaymentDtos;
import com.example.backend_booking.model.User;
import com.example.backend_booking.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation
    public ResponseEntity<PaymentDtos.PaymentResponse> pay(@RequestBody PaymentDtos.PaymentRequest req) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User user = (User) p;
        PaymentDtos.PaymentResponse resp = paymentService.processFakeCard(req, user);
        return ResponseEntity.ok(resp);
    }
}
