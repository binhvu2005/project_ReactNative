package com.example.backend_booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RoomImageDtos {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomImageDto {
        private Long id;
        private Long roomId;
        private String url;
        private Boolean isPrimary;
    }
}

