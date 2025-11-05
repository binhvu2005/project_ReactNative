package com.example.backend_booking.controller;

import com.example.backend_booking.dto.RoomDtos;
import com.example.backend_booking.model.User;
import com.example.backend_booking.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{roomId}")
    @Operation
    public ResponseEntity<?> add(@PathVariable Long roomId) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User user = (User) p;
        favoriteService.addFavorite(user, roomId);
        return ResponseEntity.status(201).body(java.util.Map.of("success", true));
    }

    @DeleteMapping("/{roomId}")
    @Operation
    public ResponseEntity<?> remove(@PathVariable Long roomId) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User user = (User) p;
        favoriteService.removeFavorite(user, roomId);
        return ResponseEntity.ok(java.util.Map.of("success", true));
    }

    @GetMapping
    @Operation
    public ResponseEntity<List<RoomDtos.RoomSummary>> list() {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User user = (User) p;
        return ResponseEntity.ok(favoriteService.listFavorites(user));
    }
}
