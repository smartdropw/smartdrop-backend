package com.smart.drop.analytics.infrastructure.persistence.jpa.repositories;

import com.smart.drop.analytics.infrastructure.persistence.jpa.entities.ReportEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportJpaRepository extends JpaRepository<ReportEntity, Integer> {

    @EntityGraph(attributePaths = "metrics")
    List<ReportEntity> findByUserId(Integer userId);
}

