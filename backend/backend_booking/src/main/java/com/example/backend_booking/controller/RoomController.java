package com.example.backend_booking.controller;

import com.example.backend_booking.dto.RoomDtos;
import com.example.backend_booking.dto.RoomImageDtos;
import com.example.backend_booking.model.RoomImage;
import com.example.backend_booking.service.RoomService;
import com.example.backend_booking.repository.RoomImageRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RoomImageRepository roomImageRepository;

    public RoomController(RoomService roomService, RoomImageRepository roomImageRepository) {
        this.roomService = roomService;
        this.roomImageRepository = roomImageRepository;
    }

    @GetMapping
    @Operation
    public ResponseEntity<?> search(@RequestParam(required = false) String city,
                                    @RequestParam(required = false) Long hotelId,
                                    @RequestParam(required = false) Long priceMin,
                                    @RequestParam(required = false) Long priceMax,
                                    @RequestParam(required = false) Integer capacity,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        Page<com.example.backend_booking.model.Room> p = roomService.search(city, hotelId, priceMin, priceMax, capacity, page, size);
        var items = p.stream().map(r -> new RoomDtos.RoomSummary(r.getId(), r.getHotel().getId(), r.getTitle(), r.getPriceCents(), r.getCurrency(), r.getCapacity(), r.getStatus())).collect(Collectors.toList());
        return ResponseEntity.ok(java.util.Map.of("items", items, "total", p.getTotalElements()));
    }

    @GetMapping("/{id}")
    @Operation
    public ResponseEntity<RoomDtos.RoomDetail> detail(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getDetail(id));
    }

    @GetMapping("/{id}/images")
    @Operation
    public ResponseEntity<List<RoomImageDtos.RoomImageDto>> images(@PathVariable Long id) {
        List<RoomImage> imgs = roomImageRepository.findByRoomId(id);
        List<RoomImageDtos.RoomImageDto> dtos = imgs.stream().map(i -> new RoomImageDtos.RoomImageDto(i.getId(), i.getRoom().getId(), i.getUrl(), i.getIsPrimary())).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
