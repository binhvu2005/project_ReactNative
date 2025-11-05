package com.example.backend_booking.controller;

import com.example.backend_booking.dto.HotelDtos;
import com.example.backend_booking.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    @Operation
    public ResponseEntity<List<HotelDtos.HotelSummary>> list(@RequestParam(required = false) String city) {
        return ResponseEntity.ok(hotelService.listByCity(city));
    }

    @GetMapping("/best")
    @Operation
    public ResponseEntity<List<HotelDtos.HotelSummary>> best() {
        return ResponseEntity.ok(hotelService.topHotels(5));
    }

    @GetMapping("/{id}")
    @Operation
    public ResponseEntity<HotelDtos.HotelDetail> detail(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getDetail(id));
    }
}
