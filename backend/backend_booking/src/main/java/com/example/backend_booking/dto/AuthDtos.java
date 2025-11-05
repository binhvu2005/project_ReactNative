package com.example.backend_booking.dto;

import java.time.OffsetDateTime;

public class AuthDtos {

    public static class RegisterRequest {
        private String email;
        private String phone;
        private String password;
        private String fullName;
        private String gender;
        private String birthDate;

        public RegisterRequest() {}
        public RegisterRequest(String email, String phone, String password, String fullName, String gender, String birthDate) {
            this.email = email; this.phone = phone; this.password = password; this.fullName = fullName; this.gender = gender; this.birthDate = birthDate;
        }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public String getBirthDate() { return birthDate; }
        public void setBirthDate(String birthDate) { this.birthDate = birthDate; }
    }

    public static class LoginRequest {
        private String emailOrPhone;
        private String password;
        public LoginRequest() {}
        public LoginRequest(String emailOrPhone, String password) { this.emailOrPhone = emailOrPhone; this.password = password; }
        public String getEmailOrPhone() { return emailOrPhone; }
        public void setEmailOrPhone(String emailOrPhone) { this.emailOrPhone = emailOrPhone; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class JwtResponse {
        private String accessToken;
        private String tokenType = "Bearer";
        private UserResponse user;

        public JwtResponse() {}
        public JwtResponse(String accessToken, UserResponse user) { this.accessToken = accessToken; this.user = user; }
        public String getAccessToken() { return accessToken; }
        public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
        public String getTokenType() { return tokenType; }
        public void setTokenType(String tokenType) { this.tokenType = tokenType; }
        public UserResponse getUser() { return user; }
        public void setUser(UserResponse user) { this.user = user; }
    }

    public static class ForgotRequest {
        private String target;
        public ForgotRequest() {}
        public ForgotRequest(String target) { this.target = target; }
        public String getTarget() { return target; }
        public void setTarget(String target) { this.target = target; }
    }

    public static class VerifyOtpRequest {
        private String target;
        private String code;
        private String type;
        public VerifyOtpRequest() {}
        public VerifyOtpRequest(String target, String code, String type) { this.target = target; this.code = code; this.type = type; }
        public String getTarget() { return target; }
        public void setTarget(String target) { this.target = target; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class ResetPasswordRequest {
        private String target;
        private String code;
        private String newPassword;
        public ResetPasswordRequest() {}
        public ResetPasswordRequest(String target, String code, String newPassword) { this.target = target; this.code = code; this.newPassword = newPassword; }
        public String getTarget() { return target; }
        public void setTarget(String target) { this.target = target; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }

    public static class UserResponse {
        private Long id;
        private String email;
        private String phone;
        private Boolean isVerified;
        private String provider;
        private OffsetDateTime createdAt;
        private OffsetDateTime updatedAt;

        public UserResponse() {}
        public UserResponse(Long id, String email, String phone, Boolean isVerified, String provider, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
            this.id = id; this.email = email; this.phone = phone; this.isVerified = isVerified; this.provider = provider; this.createdAt = createdAt; this.updatedAt = updatedAt;
        }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public Boolean getIsVerified() { return isVerified; }
        public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }
        public String getProvider() { return provider; }
        public void setProvider(String provider) { this.provider = provider; }
        public OffsetDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
        public OffsetDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
    }
}
