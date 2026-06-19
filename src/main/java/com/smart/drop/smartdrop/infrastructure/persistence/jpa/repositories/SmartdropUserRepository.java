package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.aggregates.SmartdropUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SmartDrop User entity
 */
@Repository
public interface SmartdropUserRepository extends JpaRepository<SmartdropUser, Integer> {
    Optional<SmartdropUser> findByEmail(String email);
    boolean existsByEmail(String email);
}

