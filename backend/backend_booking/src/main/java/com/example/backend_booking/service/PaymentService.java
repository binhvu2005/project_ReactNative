package com.example.backend_booking.service;

import com.example.backend_booking.dto.PaymentDtos;
import com.example.backend_booking.model.Booking;
import com.example.backend_booking.model.Payment;
import com.example.backend_booking.model.User;
import com.example.backend_booking.repository.BookingRepository;
import com.example.backend_booking.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public PaymentDtos.PaymentResponse processFakeCard(PaymentDtos.PaymentRequest req, User user) {
        Booking booking = bookingRepository.findById(req.getBookingId()).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        if (booking.getUser() == null || !booking.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Not authorized");
        }
        Payment p = Payment.builder()
                .booking(booking)
                .amountCents(booking.getTotalCents())
                .currency(booking.getCurrency())
                .provider("fakecard")
                .status("pending")
                .transactionRef(null)
                .build();
        p = paymentRepository.save(p);

        // Fake processing logic: succeed if card number ends with even digit
        String card = req.getCardNumber() == null ? "" : req.getCardNumber().replaceAll("\\D", "");
        boolean success = false;
        if (card.length() > 0) {
            char last = card.charAt(card.length() - 1);
            success = ((last - '0') % 2) == 0;
        }

        if (success) {
            p.setStatus("success");
            p.setTransactionRef(UUID.randomUUID().toString());
            booking.setStatus("confirmed");
            bookingRepository.save(booking);
        } else {
            p.setStatus("failed");
            p.setTransactionRef(UUID.randomUUID().toString());
        }
        p = paymentRepository.save(p);

        return new PaymentDtos.PaymentResponse(p.getId(), booking.getId(), p.getAmountCents(), p.getCurrency(), p.getProvider(), p.getStatus(), p.getTransactionRef(), p.getCreatedAt());
    }
}

