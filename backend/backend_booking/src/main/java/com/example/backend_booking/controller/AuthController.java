package com.example.backend_booking.controller;

import com.example.backend_booking.dto.AuthDtos;
import com.example.backend_booking.model.User;
import com.example.backend_booking.repository.UserRepository;
import com.example.backend_booking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Create a new user account and return JWT on success", tags = {"Auth"})
    public ResponseEntity<AuthDtos.JwtResponse> register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
        AuthDtos.JwtResponse resp = authService.register(req);
        return ResponseEntity.status(201).body(resp);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token", tags = {"Auth"})
    public ResponseEntity<AuthDtos.JwtResponse> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        AuthDtos.JwtResponse resp = authService.login(req);
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request password reset OTP", description = "Send OTP to the user's email/phone for password reset (development mode may expose OTP)", tags = {"Auth"})
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody AuthDtos.ForgotRequest req) {
        authService.forgotPassword(req);
        return ResponseEntity.ok().body(java.util.Map.of("message", "OTP sent (dev)"));
    }

    @PostMapping("/verify-otp")
    @Operation(summary = "Verify OTP", description = "Verify the OTP code sent to user", tags = {"Auth"})
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody AuthDtos.VerifyOtpRequest req) {
        authService.verifyOtp(req);
        return ResponseEntity.ok().body(java.util.Map.of("success", true));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset password using a verified OTP", tags = {"Auth"})
    public ResponseEntity<?> resetPassword(@Valid @RequestBody AuthDtos.ResetPasswordRequest req) {
        authService.resetPassword(req);
        return ResponseEntity.ok().body(java.util.Map.of("success", true));
    }

    @GetMapping("/me")
    @Operation(summary = "Get current user", description = "Return information about the authenticated user", tags = {"Auth"}, security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<AuthDtos.UserResponse> me() {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (p instanceof User) {
            User u = (User) p;
            AuthDtos.UserResponse ur = new AuthDtos.UserResponse(u.getId(), u.getEmail(), u.getPhone(), u.getIsVerified(), u.getProvider(), u.getCreatedAt(), u.getUpdatedAt());
            return ResponseEntity.ok(ur);
        }
        return ResponseEntity.status(401).build();
    }

    @PutMapping("/me")
    @Operation(summary = "Update current user", description = "Partially update the authenticated user's profile (email, phone)", tags = {"Auth"}, security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<AuthDtos.UserResponse> updateMe(@RequestBody java.util.Map<String, Object> updates) {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(p instanceof User)) return ResponseEntity.status(401).build();
        User u = (User) p;
        // simple partial update only for phone and email (others can be added)
        if (updates.containsKey("email")) u.setEmail((String) updates.get("email"));
        if (updates.containsKey("phone")) u.setPhone((String) updates.get("phone"));
        userRepository.save(u);
        AuthDtos.UserResponse ur = new AuthDtos.UserResponse(u.getId(), u.getEmail(), u.getPhone(), u.getIsVerified(), u.getProvider(), u.getCreatedAt(), u.getUpdatedAt());
        return ResponseEntity.ok(ur);
    }
}
