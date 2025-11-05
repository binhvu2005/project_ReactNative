package com.example.backend_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingItemReq {
        private Long roomId;
        private Integer nights;
        private Long priceCents;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingRequest {
        private LocalDate checkinDate;
        private LocalDate checkoutDate;
        private Integer guests;
        private List<BookingItemReq> items;
        private Long totalCents;
        private String currency;
        private String paymentMethod;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingDto {
        private Long id;
        private Long userId;
        private Long totalCents;
        private String currency;
        private String status;
        private LocalDate checkinDate;
        private LocalDate checkoutDate;
        private Integer guests;
        private List<BookingItemDto> items;
        private OffsetDateTime createdAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingItemDto {
        private Long id;
        private Long roomId;
        private Long priceCents;
        private Integer nights;
    }
}

