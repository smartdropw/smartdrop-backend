package com.smart.drop.analytics.infrastructure.persistence.jpa.repositories;

import com.smart.drop.analytics.infrastructure.persistence.jpa.entities.UsagePatternEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsagePatternJpaRepository extends JpaRepository<UsagePatternEntity, Integer> {

    List<UsagePatternEntity> findByUserId(Integer userId);
}
