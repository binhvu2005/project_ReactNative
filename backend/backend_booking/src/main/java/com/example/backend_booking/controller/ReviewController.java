package com.example.backend_booking.controller;

import com.example.backend_booking.dto.ReviewDtos;
import com.example.backend_booking.model.User;
import com.example.backend_booking.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms/{roomId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @Operation
    public ResponseEntity<ReviewDtos.ReviewDto> addReview(@PathVariable Long roomId, @RequestBody ReviewDtos.ReviewRequest req) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User user = (User) p;
        ReviewDtos.ReviewDto dto = reviewService.addReview(roomId, req, user);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    @Operation
    public ResponseEntity<?> list(@PathVariable Long roomId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<ReviewDtos.ReviewDto> p = reviewService.list(roomId, page, size);
        return ResponseEntity.ok(java.util.Map.of("items", p.getContent(), "total", p.getTotalElements()));
    }
}
