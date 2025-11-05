package com.example.backend_booking.repository;

import com.example.backend_booking.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r where (:city is null or lower(r.hotel.city) = lower(:city)) " +
            "and (:hotelId is null or r.hotel.id = :hotelId) " +
            "and (:priceMin is null or r.priceCents >= :priceMin) " +
            "and (:priceMax is null or r.priceCents <= :priceMax) " +
            "and (:capacity is null or r.capacity >= :capacity)")
    Page<Room> search(@Param("city") String city,
                      @Param("hotelId") Long hotelId,
                      @Param("priceMin") Long priceMin,
                      @Param("priceMax") Long priceMax,
                      @Param("capacity") Integer capacity,
                      Pageable pageable);
}

