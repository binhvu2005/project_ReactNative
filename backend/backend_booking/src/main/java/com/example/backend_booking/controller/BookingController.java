package com.example.backend_booking.controller;

import com.example.backend_booking.dto.BookingDtos;
import com.example.backend_booking.model.User;
import com.example.backend_booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation
    public ResponseEntity<BookingDtos.BookingDto> create(@RequestBody BookingDtos.BookingRequest req) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User user = (User) p;
        BookingDtos.BookingDto dto = bookingService.createBooking(req, user);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    @Operation
    public ResponseEntity<List<BookingDtos.BookingDto>> list() {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User user = (User) p;
        return ResponseEntity.ok(bookingService.listByUser(user.getId()));
    }

    @GetMapping("/{id}")
    @Operation
    public ResponseEntity<BookingDtos.BookingDto> detail(@PathVariable Long id) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        BookingDtos.BookingDto dto = bookingService.getDetail(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/cancel")
    @Operation
    public ResponseEntity<?> cancel(@PathVariable Long id) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User user = (User) p;
        bookingService.cancelBooking(id, user.getId());
        return ResponseEntity.ok(java.util.Map.of("success", true));
    }
}
