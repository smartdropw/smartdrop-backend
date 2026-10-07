package com.smart.drop.analytics.domain.model.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pruebas Unitarias - Patrón Strategy: Detección de Fugas Nocturnas")
class NightLeakDetectionStrategyTest {

    private NightLeakDetectionStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new NightLeakDetectionStrategy();
    }

    @Test
    @DisplayName("Debe disparar alerta CRITICAL ante un descenso volumétrico anómalo en horario nocturno (03:00h)")
    void shouldTriggerCriticalAlertDuringNightHoursWhenThresholdExceeded() {
        // Arrange: 12.0 cm/h a las 03:00 AM con umbral de 5.0 cm/h
        double dropRate = 12.0;
        double threshold = 5.0;
        int hour = 3;

        // Act
        AlertEvaluationResult result = strategy.evaluate(dropRate, threshold, hour);

        // Assert
        assertThat(result.alertTriggered()).isTrue();
        assertThat(result.severity()).isEqualTo("CRITICAL");
        assertThat(result.strategyName()).isEqualTo("NightLeakDetectionStrategy");
        assertThat(result.message()).contains("Fuga nocturna detectada");
        assertThat(result.recommendedAction()).contains("Cerrar inmediatamente la válvula matriz");
    }

    @Test
    @DisplayName("No debe disparar alerta si el consumo ocurre en horario diurno (14:00h)")
    void shouldNotTriggerAlertDuringDaytimeHours() {
        // Arrange: 15.0 cm/h a las 14:00 (consumo humano regular)
        double dropRate = 15.0;
        double threshold = 5.0;
        int hour = 14;

        // Act
        AlertEvaluationResult result = strategy.evaluate(dropRate, threshold, hour);

        // Assert
        assertThat(result.alertTriggered()).isFalse();
        assertThat(result.severity()).isEqualTo("NONE");
        assertThat(result.message()).contains("Horario diurno");
    }

    @Test
    @DisplayName("No debe disparar alerta en horario nocturno si el descenso es inferior al umbral")
    void shouldNotTriggerAlertWhenDropRateIsBelowThreshold() {
        // Arrange: 2.0 cm/h a las 04:00 AM (tolerancia normal)
        double dropRate = 2.0;
        double threshold = 5.0;
        int hour = 4;

        // Act
        AlertEvaluationResult result = strategy.evaluate(dropRate, threshold, hour);

        // Assert
        assertThat(result.alertTriggered()).isFalse();
        assertThat(result.message()).contains("Monitoreo nocturno estable");
    }
}
