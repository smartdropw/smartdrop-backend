package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.AlertRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SmartDrop AlertRule entity
 */
@Repository
public interface SmartdropAlertRuleRepository extends JpaRepository<AlertRule, Integer> {
    List<AlertRule> findByUserId(Integer userId);
}

