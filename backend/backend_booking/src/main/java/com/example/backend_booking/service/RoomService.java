package com.example.backend_booking.service;

import com.example.backend_booking.dto.RoomDtos;
import com.example.backend_booking.model.Room;
import com.example.backend_booking.repository.RoomImageRepository;
import com.example.backend_booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;

    public Page<Room> search(String city, Long hotelId, Long priceMin, Long priceMax, Integer capacity, int page, int size) {
        return roomRepository.search(city, hotelId, priceMin, priceMax, capacity, PageRequest.of(page, size));
    }

    public RoomDtos.RoomDetail getDetail(Long roomId) {
        Room r = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        RoomDtos.RoomDetail d = new RoomDtos.RoomDetail();
        d.setId(r.getId());
        d.setHotelId(r.getHotel().getId());
        d.setCode(r.getCode());
        d.setTitle(r.getTitle());
        d.setDescription(r.getDescription());
        d.setPriceCents(r.getPriceCents());
        d.setCurrency(r.getCurrency());
        d.setCapacity(r.getCapacity());
        d.setBeds(r.getBeds());
        d.setAreaM2(r.getAreaM2());
        d.setStatus(r.getStatus());
        d.setCreatedAt(r.getCreatedAt());
        d.setUpdatedAt(r.getUpdatedAt());
        // images
        var imgs = roomImageRepository.findByRoomId(r.getId());
        d.setImages(imgs.stream().map(i -> new com.example.backend_booking.dto.RoomImageDtos.RoomImageDto(i.getId(), i.getRoom().getId(), i.getUrl(), i.getIsPrimary())).collect(Collectors.toList()));
        // amenities, reviews omitted for brevity; can be added later
        return d;
    }
}

