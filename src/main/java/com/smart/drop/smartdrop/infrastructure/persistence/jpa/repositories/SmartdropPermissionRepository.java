package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SmartDrop Permission entity
 */
@Repository
public interface SmartdropPermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByName(String name);
}

