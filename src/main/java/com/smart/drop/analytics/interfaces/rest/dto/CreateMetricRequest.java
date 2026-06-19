package com.smart.drop.analytics.interfaces.rest.dto;

import java.math.BigDecimal;

public record CreateMetricRequest(
        String name,
        BigDecimal value,
        String unit
) {
}

