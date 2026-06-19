package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SmartDrop Consumption entity
 */
@Repository
public interface SmartdropConsumptionRepository extends JpaRepository<Consumption, Integer> {
    List<Consumption> findByUserId(Integer userId);
}

