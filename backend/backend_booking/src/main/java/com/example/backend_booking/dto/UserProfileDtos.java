package com.example.backend_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class UserProfileDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfileRequest {
        private String fullName;
        private String gender;
        private LocalDate birthDate;
        private String avatarUrl;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfileResponse {
        private Long id;
        private Long userId;
        private String fullName;
        private String gender;
        private LocalDate birthDate;
        private String avatarUrl;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;
    }
}

