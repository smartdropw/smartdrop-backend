package com.smart.drop.analytics.domain.model.entities;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Immutable domain entity for reports.
 */
public record Report(
        Integer reportId,
        Integer userId,
        String title,
        String content,
        String type,
        LocalDateTime generatedAt,
        List<Metric> metrics
) {
    public Report {
        if (title != null && title.length() > 100) {
            throw new IllegalArgumentException("title must be <= 100 characters");
        }
        if (type != null && type.length() > 50) {
            throw new IllegalArgumentException("type must be <= 50 characters");
        }
        metrics = metrics == null ? List.of() : List.copyOf(metrics);
    }

    public static Report create(Integer userId, String title, String content, String type) {
        return new Report(null, userId, title, content, type, LocalDateTime.now(), List.of());
    }

    public Report withMetrics(List<Metric> metrics) {
        return new Report(reportId, userId, title, content, type, generatedAt, metrics);
    }
}

