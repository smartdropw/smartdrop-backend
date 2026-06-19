package com.smart.drop.smartdrop.application.services;

import com.smart.drop.smartdrop.domain.model.aggregates.SmartdropUser;
import com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories.SmartdropUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for SmartDrop User business logic
 */
@Service
@Profile("legacy")
public class UserService {

    @Autowired
    private SmartdropUserRepository smartdropUserRepository;

    public SmartdropUser createUser(String fullName, String email, String passwordHash) {
        SmartdropUser user = new SmartdropUser(fullName, email, passwordHash);
        return smartdropUserRepository.save(user);
    }

    public Optional<SmartdropUser> findByEmail(String email) {
        return smartdropUserRepository.findByEmail(email);
    }

    public Optional<SmartdropUser> findById(Integer userId) {
        return smartdropUserRepository.findById(userId);
    }

    public List<SmartdropUser> findAll() {
        return smartdropUserRepository.findAll();
    }

    public void deleteUser(Integer userId) {
        smartdropUserRepository.deleteById(userId);
    }

    public SmartdropUser updateUser(Integer userId, String fullName, String email) {
        Optional<SmartdropUser> user = smartdropUserRepository.findById(userId);
        if (user.isPresent()) {
            SmartdropUser u = user.get();
            u.setFullName(fullName);
            u.setEmail(email);
            return smartdropUserRepository.save(u);
        }
        return null;
    }
}

