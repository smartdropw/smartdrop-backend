package com.smart.drop.analytics.interfaces.rest.dto;

import java.util.List;

public record CreateReportRequest(
        Integer userId,
        String title,
        String content,
        String type,
        List<CreateMetricRequest> metrics
) {
}

