package com.example.backend_booking.repository;

import com.example.backend_booking.model.Hotel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCityIgnoreCase(String city);

    @Query("select h from Hotel h order by h.star desc")
    List<Hotel> findTopHotelsByStar(Pageable pageable);
}

