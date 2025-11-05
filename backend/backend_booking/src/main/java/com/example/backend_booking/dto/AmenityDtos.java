package com.example.backend_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AmenityDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AmenityDto {
        private Long id;
        private String name;
    }
}

