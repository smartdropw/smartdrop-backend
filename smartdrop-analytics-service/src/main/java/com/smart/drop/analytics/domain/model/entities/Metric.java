package com.smart.drop.analytics.domain.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Immutable domain entity for dashboard metrics.
 */
public record Metric(
        Integer metricId,
        Integer userId,
        Integer reportId,
        String name,
        BigDecimal value,
        String unit,
        LocalDateTime recordedAt
) {
    public Metric {
        if (name != null && name.length() > 50) {
            throw new IllegalArgumentException("name must be <= 50 characters");
        }
        if (unit != null && unit.length() > 20) {
            throw new IllegalArgumentException("unit must be <= 20 characters");
        }
    }

    public static Metric create(Integer userId, Integer reportId, String name, BigDecimal value, String unit) {
        return new Metric(null, userId, reportId, name, value, unit, LocalDateTime.now());
    }
}

