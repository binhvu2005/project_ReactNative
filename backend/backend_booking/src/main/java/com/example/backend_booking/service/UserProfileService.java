package com.example.backend_booking.service;

import com.example.backend_booking.dto.UserProfileDtos;
import com.example.backend_booking.model.User;
import com.example.backend_booking.model.UserProfile;
import com.example.backend_booking.repository.UserProfileRepository;
import com.example.backend_booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileDtos.UserProfileResponse getByUser(User user) {
        Optional<UserProfile> maybe = userProfileRepository.findByUserId(user.getId());
        if (maybe.isEmpty()) return null;
        UserProfile p = maybe.get();
        return new UserProfileDtos.UserProfileResponse(p.getId(), p.getUser().getId(), p.getFullName(), p.getGender(), p.getBirthDate(), p.getAvatarUrl(), p.getCreatedAt(), p.getUpdatedAt());
    }

    public UserProfileDtos.UserProfileResponse upsert(User user, UserProfileDtos.UserProfileRequest req) {
        UserProfile profile = userProfileRepository.findByUserId(user.getId()).orElse(null);
        if (profile == null) {
            profile = UserProfile.builder().user(user).fullName(req.getFullName()).gender(req.getGender()).birthDate(req.getBirthDate()).avatarUrl(req.getAvatarUrl()).build();
        } else {
            if (req.getFullName() != null) profile.setFullName(req.getFullName());
            if (req.getGender() != null) profile.setGender(req.getGender());
            if (req.getBirthDate() != null) profile.setBirthDate(req.getBirthDate());
            if (req.getAvatarUrl() != null) profile.setAvatarUrl(req.getAvatarUrl());
        }
        profile = userProfileRepository.save(profile);
        return new UserProfileDtos.UserProfileResponse(profile.getId(), profile.getUser().getId(), profile.getFullName(), profile.getGender(), profile.getBirthDate(), profile.getAvatarUrl(), profile.getCreatedAt(), profile.getUpdatedAt());
    }
}

