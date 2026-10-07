package com.smart.drop.analytics.application.queries;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Read-optimized model for dashboard screens.
 */
public record DashboardReadModel(
        Integer userId,
        long totalReports,
        long totalMetrics,
        BigDecimal averageMetricValue,
        LocalDateTime lastReportGeneratedAt,
        List<MetricCard> recentMetrics,
        List<ReportCard> recentReports
) {
    public record MetricCard(
            Integer metricId,
            Integer reportId,
            String name,
            BigDecimal value,
            String unit,
            LocalDateTime recordedAt
    ) {
    }

    public record ReportCard(
            Integer reportId,
            String title,
            String type,
            LocalDateTime generatedAt,
            int metricCount
    ) {
    }
}

