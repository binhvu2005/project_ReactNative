package com.example.backend_booking.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorites")
@IdClass(FavoriteId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "created_at")
    private java.time.OffsetDateTime createdAt;
}
