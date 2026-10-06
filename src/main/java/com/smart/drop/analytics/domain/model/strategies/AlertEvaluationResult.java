package com.smart.drop.analytics.domain.model.strategies;

import java.time.LocalDateTime;

/**
 * Resultado inmutable de la evaluación de una regla mediante el patrón Strategy.
 */
public record AlertEvaluationResult(
        boolean alertTriggered,
        String severity,
        String strategyName,
        String message,
        String recommendedAction,
        LocalDateTime evaluatedAt
) {
    public static AlertEvaluationResult noAlert(String strategyName, String message) {
        return new AlertEvaluationResult(false, "NONE", strategyName, message, "N/A", LocalDateTime.now());
    }

    public static AlertEvaluationResult triggered(String strategyName, String severity, String message, String action) {
        return new AlertEvaluationResult(true, severity, strategyName, message, action, LocalDateTime.now());
    }
}
