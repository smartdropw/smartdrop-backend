package com.smart.drop.smartdrop.application.services;

import com.smart.drop.smartdrop.domain.model.entities.Profile;
import com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories.SmartdropProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for Profile business logic
 */
@Service
@org.springframework.context.annotation.Profile("legacy")
public class ProfileService {

    @Autowired
    private SmartdropProfileRepository smartdropProfileRepository;

    public Profile createProfile(Integer userId, String fullName, String email) {
        Profile profile = new Profile(userId, fullName, email);
        return smartdropProfileRepository.save(profile);
    }

    public Optional<Profile> findByUserId(Integer userId) {
        return smartdropProfileRepository.findByUserId(userId);
    }

    public Optional<Profile> findById(Integer profileId) {
        return smartdropProfileRepository.findById(profileId);
    }

    public List<Profile> findAll() {
        return smartdropProfileRepository.findAll();
    }

    public Profile updateProfile(Integer profileId, String fullName, String email, String language, Boolean notificationsEnabled) {
        Optional<Profile> profile = smartdropProfileRepository.findById(profileId);
        if (profile.isPresent()) {
            Profile p = profile.get();
            p.setFullName(fullName);
            p.setEmail(email);
            p.setLanguage(language);
            p.setNotificationsEnabled(notificationsEnabled);
            p.setUpdatedAt(java.time.LocalDateTime.now());
            return smartdropProfileRepository.save(p);
        }
        return null;
    }
}

