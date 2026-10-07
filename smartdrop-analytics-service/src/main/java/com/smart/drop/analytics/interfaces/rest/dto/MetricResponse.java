package com.smart.drop.analytics.interfaces.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MetricResponse(
        Integer metricId,
        Integer userId,
        Integer reportId,
        String name,
        BigDecimal value,
        String unit,
        LocalDateTime recordedAt
) {
}

