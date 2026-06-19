package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for SmartDrop Subscription entity
 */
@Repository
public interface SmartdropSubscriptionRepository extends JpaRepository<Subscription, Integer> {
    List<Subscription> findByUserId(Integer userId);
    Optional<Subscription> findByUserIdAndStatus(Integer userId, String status);
}

