package com.example.backend_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

public class HotelDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelSummary {
        private Long id;
        private String name;
        private String city;
        private String country;
        private Short star;
        private String image;
        private Long priceCents;
        private Integer reviews;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelDetail {
        private Long id;
        private String name;
        private String description;
        private String address;
        private String city;
        private String country;
        private Short star;
        private Double lat;
        private Double lng;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
        private List<com.example.backend_booking.dto.RoomDtos.RoomSummary> rooms;
    }
}

