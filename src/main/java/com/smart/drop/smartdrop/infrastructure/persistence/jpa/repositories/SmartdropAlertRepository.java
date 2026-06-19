package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SmartDrop Alert entity
 */
@Repository
public interface SmartdropAlertRepository extends JpaRepository<Alert, Integer> {
    List<Alert> findByUserId(Integer userId);
}

