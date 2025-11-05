package com.example.backend_booking.repository;

import com.example.backend_booking.model.Favorite;
import com.example.backend_booking.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
    List<Favorite> findByUserId(Long userId);
    boolean existsByUserIdAndRoomId(Long userId, Long roomId);
    void deleteByUserIdAndRoomId(Long userId, Long roomId);
}
