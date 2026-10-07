package com.smart.drop.analytics.infrastructure.persistence.jpa.repositories;

import com.smart.drop.analytics.infrastructure.persistence.jpa.entities.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetricJpaRepository extends JpaRepository<MetricEntity, Integer> {

    List<MetricEntity> findByUserId(Integer userId);

    List<MetricEntity> findByReport_ReportId(Integer reportId);
}

