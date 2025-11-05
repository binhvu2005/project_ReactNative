package com.example.backend_booking.service;

import com.example.backend_booking.dto.HotelDtos;
import com.example.backend_booking.dto.RoomDtos;
import com.example.backend_booking.model.Hotel;
import com.example.backend_booking.repository.HotelRepository;
import com.example.backend_booking.repository.RoomImageRepository;
import com.example.backend_booking.repository.RoomRepository;
import com.example.backend_booking.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;
    private final ReviewRepository reviewRepository;

    public List<HotelDtos.HotelSummary> listByCity(String city) {
        List<Hotel> hotels = city == null ? hotelRepository.findAll() : hotelRepository.findByCityIgnoreCase(city);
        return hotels.stream().map(this::toHotelSummary).collect(Collectors.toList());
    }

    public List<HotelDtos.HotelSummary> topHotels(int limit) {
        return hotelRepository.findTopHotelsByStar(PageRequest.of(0, limit))
                .stream()
                .map(this::toHotelSummary)
                .collect(Collectors.toList());
    }

    private HotelDtos.HotelSummary toHotelSummary(Hotel h) {
        // Get first room's primary image
        var roomsPage = roomRepository.search(h.getCity(), h.getId(), null, null, null, PageRequest.of(0, 1));
        String image = null;
        Long priceCents = null;
        Integer reviews = 0;
        
        if (!roomsPage.getContent().isEmpty()) {
            var firstRoom = roomsPage.getContent().get(0);
            priceCents = firstRoom.getPriceCents();
            var images = roomImageRepository.findByRoomId(firstRoom.getId());
            image = images.stream()
                    .filter(img -> Boolean.TRUE.equals(img.getIsPrimary()))
                    .findFirst()
                    .map(img -> img.getUrl())
                    .orElse(images.isEmpty() ? null : images.get(0).getUrl());
            
            // Count total reviews for all rooms in hotel
            var allRoomsPage = roomRepository.search(h.getCity(), h.getId(), null, null, null, PageRequest.of(0, 100));
            reviews = (int) allRoomsPage.getContent().stream()
                    .mapToLong(room -> reviewRepository.findByRoomId(room.getId(), PageRequest.of(0, 1)).getTotalElements())
                    .sum();
        }
        
        return new HotelDtos.HotelSummary(
                h.getId(),
                h.getName(),
                h.getCity(),
                h.getCountry(),
                h.getStar(),
                image,
                priceCents,
                reviews
        );
    }

    public HotelDtos.HotelDetail getDetail(Long hotelId) {
        Hotel h = hotelRepository.findById(hotelId).orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
        HotelDtos.HotelDetail d = new HotelDtos.HotelDetail();
        d.setId(h.getId());
        d.setName(h.getName());
        d.setDescription(h.getDescription());
        d.setAddress(h.getAddress());
        d.setCity(h.getCity());
        d.setCountry(h.getCountry());
        d.setStar(h.getStar());
        d.setLat(h.getLat());
        d.setLng(h.getLng());
        d.setCreatedAt(h.getCreatedAt());
        d.setUpdatedAt(h.getUpdatedAt());
        // rooms summary
        var roomsPage = roomRepository.search(h.getCity(), h.getId(), null, null, null, PageRequest.of(0, 100));
        List<RoomDtos.RoomSummary> rooms = roomsPage.stream().map(r -> new RoomDtos.RoomSummary(r.getId(), r.getHotel().getId(), r.getTitle(), r.getPriceCents(), r.getCurrency(), r.getCapacity(), r.getStatus())).collect(Collectors.toList());
        d.setRooms(rooms);
        return d;
    }
}

