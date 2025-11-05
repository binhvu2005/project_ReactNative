package com.example.backend_booking.service;

import com.example.backend_booking.dto.RoomDtos;
import com.example.backend_booking.model.Favorite;
import com.example.backend_booking.model.Room;
import com.example.backend_booking.model.FavoriteId;
import com.example.backend_booking.model.User;
import com.example.backend_booking.repository.FavoriteRepository;
import com.example.backend_booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public void addFavorite(User user, Long roomId) {
        if (favoriteRepository.existsByUserIdAndRoomId(user.getId(), roomId)) return;
        Favorite f = Favorite.builder().userId(user.getId()).roomId(roomId).createdAt(java.time.OffsetDateTime.now()).build();
        favoriteRepository.save(f);
    }

    @Transactional
    public void removeFavorite(User user, Long roomId) {
        favoriteRepository.deleteByUserIdAndRoomId(user.getId(), roomId);
    }

    public List<RoomDtos.RoomSummary> listFavorites(User user) {
        List<Favorite> favs = favoriteRepository.findByUserId(user.getId());
        return favs.stream().map(f -> {
            Room r = roomRepository.findById(f.getRoomId()).orElse(null);
            if (r == null) return null;
            return new RoomDtos.RoomSummary(r.getId(), r.getHotel().getId(), r.getTitle(), r.getPriceCents(), r.getCurrency(), r.getCapacity(), r.getStatus());
        }).filter(java.util.Objects::nonNull).collect(Collectors.toList());
    }
}

