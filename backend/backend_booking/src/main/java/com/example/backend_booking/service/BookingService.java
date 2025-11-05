package com.example.backend_booking.service;

import com.example.backend_booking.dto.BookingDtos;
import com.example.backend_booking.model.Booking;
import com.example.backend_booking.model.BookingItem;
import com.example.backend_booking.model.Room;
import com.example.backend_booking.model.User;
import com.example.backend_booking.repository.BookingItemRepository;
import com.example.backend_booking.repository.BookingRepository;
import com.example.backend_booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingItemRepository bookingItemRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public BookingDtos.BookingDto createBooking(BookingDtos.BookingRequest req, User user) {
        Booking b = Booking.builder()
                .user(user)
                .totalCents(req.getTotalCents())
                .currency(req.getCurrency())
                .status("pending")
                .checkinDate(req.getCheckinDate())
                .checkoutDate(req.getCheckoutDate())
                .guests(req.getGuests())
                .build();
        // save to a final local variable to use inside lambda
        final Booking savedBooking = bookingRepository.save(b);
        List<BookingItem> items = req.getItems().stream().map(it -> {
            Room room = roomRepository.findById(it.getRoomId()).orElseThrow(() -> new IllegalArgumentException("Room not found"));
            BookingItem bi = BookingItem.builder().booking(savedBooking).room(room).priceCents(it.getPriceCents()).nights(it.getNights()).build();
            return bookingItemRepository.save(bi);
        }).collect(Collectors.toList());
        savedBooking.setItems(items);
        bookingRepository.save(savedBooking);
        BookingDtos.BookingDto dto = new BookingDtos.BookingDto(savedBooking.getId(), user.getId(), savedBooking.getTotalCents(), savedBooking.getCurrency(), savedBooking.getStatus(), savedBooking.getCheckinDate(), savedBooking.getCheckoutDate(), savedBooking.getGuests(), items.stream().map(i -> new BookingDtos.BookingItemDto(i.getId(), i.getRoom().getId(), i.getPriceCents(), i.getNights())).collect(Collectors.toList()), savedBooking.getCreatedAt());
        return dto;
    }

    public List<BookingDtos.BookingDto> listByUser(Long userId) {
        return bookingRepository.findByUserId(userId).stream().map(b -> new BookingDtos.BookingDto(b.getId(), b.getUser() == null ? null : b.getUser().getId(), b.getTotalCents(), b.getCurrency(), b.getStatus(), b.getCheckinDate(), b.getCheckoutDate(), b.getGuests(), b.getItems() == null ? List.of() : b.getItems().stream().map(i -> new BookingDtos.BookingItemDto(i.getId(), i.getRoom().getId(), i.getPriceCents(), i.getNights())).collect(Collectors.toList()), b.getCreatedAt())).collect(Collectors.toList());
    }

    public BookingDtos.BookingDto getDetail(Long bookingId) {
        Booking b = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        return new BookingDtos.BookingDto(b.getId(), b.getUser() == null ? null : b.getUser().getId(), b.getTotalCents(), b.getCurrency(), b.getStatus(), b.getCheckinDate(), b.getCheckoutDate(), b.getGuests(), b.getItems() == null ? List.of() : b.getItems().stream().map(i -> new BookingDtos.BookingItemDto(i.getId(), i.getRoom().getId(), i.getPriceCents(), i.getNights())).collect(Collectors.toList()), b.getCreatedAt());
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking b = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        if (b.getUser() == null || !b.getUser().getId().equals(userId)) throw new IllegalArgumentException("Not authorized");
        b.setStatus("cancelled");
        bookingRepository.save(b);
    }
}
