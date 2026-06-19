package com.smart.drop.smartdrop.interfaces.rest;

import com.smart.drop.smartdrop.application.services.ProfileService;
import com.smart.drop.smartdrop.domain.model.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller para gestionar perfiles de usuarios en smartdrop_db
 */
@RestController
@RequestMapping("/api/v1/legacy/profiles")
@org.springframework.context.annotation.Profile("legacy")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestParam Integer userId, 
                                                   @RequestParam String fullName, 
                                                   @RequestParam String email) {
        Profile profile = profileService.createProfile(userId, fullName, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfile(@PathVariable Integer profileId) {
        Optional<Profile> profile = profileService.findById(profileId);
        return profile.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable Integer userId) {
        Optional<Profile> profile = profileService.findByUserId(userId);
        return profile.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getAllProfiles() {
        return ResponseEntity.ok(profileService.findAll());
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Integer profileId,
                                                  @RequestParam String fullName,
                                                  @RequestParam String email,
                                                  @RequestParam(required = false) String language,
                                                  @RequestParam(required = false) Boolean notificationsEnabled) {
        Profile profile = profileService.updateProfile(profileId, fullName, email, language, notificationsEnabled);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        }
        return ResponseEntity.notFound().build();
    }
}

