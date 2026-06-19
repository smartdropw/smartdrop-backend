package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SmartDrop Notification entity
 */
@Repository
public interface SmartdropNotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserId(Integer userId);
    List<Notification> findByUserIdAndStatus(Integer userId, String status);
}

