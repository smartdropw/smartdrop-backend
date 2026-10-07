package com.smart.drop.analytics.interfaces.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReportResponse(
        Integer reportId,
        Integer userId,
        String title,
        String content,
        String type,
        LocalDateTime generatedAt,
        List<MetricResponse> metrics
) {
}

