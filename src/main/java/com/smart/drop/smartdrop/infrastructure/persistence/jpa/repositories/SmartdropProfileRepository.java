package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SmartDrop Profile entity
 */
@Repository
public interface SmartdropProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUserId(Integer userId);
}

