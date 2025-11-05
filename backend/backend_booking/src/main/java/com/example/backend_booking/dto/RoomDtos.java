package com.example.backend_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

public class RoomDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomSummary {
        private Long id;
        private Long hotelId;
        private String title;
        private Long priceCents;
        private String currency;
        private Integer capacity;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomDetail {
        private Long id;
        private Long hotelId;
        private String code;
        private String title;
        private String description;
        private Long priceCents;
        private String currency;
        private Integer capacity;
        private Integer beds;
        private Integer areaM2;
        private String status;
        private List<com.example.backend_booking.dto.RoomImageDtos.RoomImageDto> images;
        private List<com.example.backend_booking.dto.AmenityDtos.AmenityDto> amenities;
        private List<com.example.backend_booking.dto.ReviewDtos.ReviewDto> reviews;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
    }
}

