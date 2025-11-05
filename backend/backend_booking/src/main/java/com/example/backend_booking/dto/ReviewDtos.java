package com.example.backend_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

public class ReviewDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewRequest {
        private Short rating;
        private String title;
        private String content;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDto {
        private Long id;
        private Long roomId;
        private Long userId;
        private Short rating;
        private String title;
        private String content;
        private OffsetDateTime createdAt;
    }
}

