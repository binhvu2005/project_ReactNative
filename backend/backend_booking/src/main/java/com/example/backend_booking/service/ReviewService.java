package com.example.backend_booking.service;

import com.example.backend_booking.dto.ReviewDtos;
import com.example.backend_booking.model.Review;
import com.example.backend_booking.model.Room;
import com.example.backend_booking.model.User;
import com.example.backend_booking.repository.ReviewRepository;
import com.example.backend_booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;

    public ReviewDtos.ReviewDto addReview(Long roomId, ReviewDtos.ReviewRequest req, User user) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        Review rev = Review.builder()
                .room(room)
                .user(user)
                .rating(req.getRating())
                .title(req.getTitle())
                .content(req.getContent())
                .build();
        rev = reviewRepository.save(rev);
        return new ReviewDtos.ReviewDto(rev.getId(), room.getId(), user.getId(), rev.getRating(), rev.getTitle(), rev.getContent(), rev.getCreatedAt());
    }

    public Page<ReviewDtos.ReviewDto> list(Long roomId, int page, int size) {
        return reviewRepository.findByRoomId(roomId, PageRequest.of(page, size)).map(r -> new ReviewDtos.ReviewDto(r.getId(), r.getRoom().getId(), r.getUser() == null ? null : r.getUser().getId(), r.getRating(), r.getTitle(), r.getContent(), r.getCreatedAt()));
    }
}

