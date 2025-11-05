package com.example.backend_booking.service;

import com.example.backend_booking.dto.AuthDtos;
import com.example.backend_booking.model.Otp;
import com.example.backend_booking.model.User;
import com.example.backend_booking.repository.OtpRepository;
import com.example.backend_booking.repository.UserRepository;
import com.example.backend_booking.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final int OTP_TTL_MINUTES = 10;

    public AuthDtos.JwtResponse register(AuthDtos.RegisterRequest req) {
        if (req.getEmail() != null && userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (req.getPhone() != null && userRepository.existsByPhone(req.getPhone())) {
            throw new IllegalArgumentException("Phone already in use");
        }

        User user = User.builder()
                .email(req.getEmail())
                .phone(req.getPhone())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .isVerified(false)
                .provider("local")
                .build();
        user = userRepository.save(user);

        // create verify OTP
        String code = generateOtp();
        Otp otp = Otp.builder()
                .user(user)
                .target(req.getEmail() != null ? req.getEmail() : req.getPhone())
                .code(code)
                .type("verify_account")
                .expiresAt(OffsetDateTime.now().plusMinutes(OTP_TTL_MINUTES))
                .used(false)
                .build();
        otpRepository.save(otp);

        // In dev we "send" by logging (System.out)
        System.out.printf("[DEV] Send OTP %s to %s for verify_account\n", code, otp.getTarget());

        String token = jwtUtil.generateToken(user.getId());
        return buildJwtResponse(token, user);
    }

    public AuthDtos.JwtResponse login(AuthDtos.LoginRequest req) {
        Optional<User> maybeUser = Optional.empty();
        if (req.getEmailOrPhone() == null) throw new IllegalArgumentException("emailOrPhone required");
        if (req.getEmailOrPhone().contains("@")) {
            maybeUser = userRepository.findByEmail(req.getEmailOrPhone());
        } else {
            maybeUser = userRepository.findByPhone(req.getEmailOrPhone());
        }
        User user = maybeUser.orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getId());
        return buildJwtResponse(token, user);
    }

    public void forgotPassword(AuthDtos.ForgotRequest req) {
        if (req.getTarget() == null) throw new IllegalArgumentException("target required");
        Optional<User> maybeUser;
        if (req.getTarget().contains("@")) maybeUser = userRepository.findByEmail(req.getTarget());
        else maybeUser = userRepository.findByPhone(req.getTarget());
        User user = maybeUser.orElseThrow(() -> new IllegalArgumentException("User not found"));

        String code = generateOtp();
        Otp otp = Otp.builder()
                .user(user)
                .target(req.getTarget())
                .code(code)
                .type("reset_password")
                .expiresAt(OffsetDateTime.now().plusMinutes(OTP_TTL_MINUTES))
                .used(false)
                .build();
        otpRepository.save(otp);
        System.out.printf("[DEV] Send OTP %s to %s for reset_password\n", code, otp.getTarget());
    }

    public void verifyOtp(AuthDtos.VerifyOtpRequest req) {
        AuthDtos.VerifyOtpRequest r = req;
        Otp otp = otpRepository.findFirstByTargetAndCodeAndTypeAndUsedFalseAndExpiresAtAfter(r.getTarget(), r.getCode(), r.getType(), OffsetDateTime.now())
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired code"));

        otp.setUsed(true);
        otpRepository.save(otp);

        if ("verify_account".equals(r.getType())) {
            User u = otp.getUser();
            if (u != null) {
                u.setIsVerified(true);
                userRepository.save(u);
            }
        }
    }

    public void resetPassword(AuthDtos.ResetPasswordRequest req) {
        Otp otp = otpRepository.findFirstByTargetAndCodeAndTypeAndUsedFalseAndExpiresAtAfter(req.getTarget(), req.getCode(), "reset_password", OffsetDateTime.now())
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired code"));
        User user = otp.getUser();
        if (user == null) throw new IllegalArgumentException("User not found");
        user.setPasswordHash(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
        otp.setUsed(true);
        otpRepository.save(otp);
    }

    private String generateOtp() {
        Random rnd = new Random();
        int code = 100000 + rnd.nextInt(900000);
        return String.valueOf(code);
    }

    private AuthDtos.JwtResponse buildJwtResponse(String token, User user) {
        AuthDtos.UserResponse ur = new AuthDtos.UserResponse(
                user.getId(), user.getEmail(), user.getPhone(), user.getIsVerified(), user.getProvider(), user.getCreatedAt(), user.getUpdatedAt()
        );
        AuthDtos.JwtResponse resp = new AuthDtos.JwtResponse();
        resp.setAccessToken(token);
        resp.setUser(ur);
        return resp;
    }
}

