package com.smart.drop.smartdrop.infrastructure.persistence.jpa.repositories;

import com.smart.drop.smartdrop.domain.model.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SmartDrop Report entity
 */
@Repository
public interface SmartdropReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByUserId(Integer userId);
}

