package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for SmartDrop Inventory entity
 */
@Repository
public interface SmartdropInventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByUserId(Integer userId);
}

