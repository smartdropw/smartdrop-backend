package com.smart.drop.analytics.domain.model.repositories;

import com.smart.drop.analytics.domain.model.entities.UsagePattern;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for usage patterns.
 */
public interface UsagePatternRepository {

    UsagePattern save(UsagePattern usagePattern);

    Optional<UsagePattern> findById(Integer patternId);

    List<UsagePattern> findByUserId(Integer userId);

    List<UsagePattern> findAll();

    void deleteById(Integer patternId);
}
