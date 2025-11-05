package com.example.backend_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

public class PaymentDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentRequest {
        private Long bookingId;
        private String cardNumber;
        private String expiry;
        private String cvv;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentResponse {
        private Long id;
        private Long bookingId;
        private Long amountCents;
        private String currency;
        private String provider;
        private String status;
        private String transactionRef;
        private OffsetDateTime createdAt;
    }
}

