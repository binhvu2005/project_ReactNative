package com.example.backend_booking.repository;

import com.example.backend_booking.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findFirstByTargetAndCodeAndTypeAndUsedFalseAndExpiresAtAfter(String target, String code, String type, OffsetDateTime now);
}
