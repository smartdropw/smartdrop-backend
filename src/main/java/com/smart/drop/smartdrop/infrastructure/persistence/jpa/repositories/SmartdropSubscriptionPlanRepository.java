package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SmartDrop SubscriptionPlan entity
 */
@Repository
public interface SmartdropSubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {
}

