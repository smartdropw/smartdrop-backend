package com.smart.drop.analytics.domain.model.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pruebas Unitarias - Patrón Strategy: Detección de Excursión Térmica en Cerveza")
class ThermalExcursionStrategyTest {

    private ThermalExcursionStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new ThermalExcursionStrategy();
    }

    @Test
    @DisplayName("Debe disparar alerta CRITICAL si la temperatura del lote cervecero supera por más de 2°C el umbral")
    void shouldTriggerCriticalAlertWhenExcursionIsSevere() {
        // Arrange: 23.5°C vs umbral máx. de 20.5°C (+3.0°C delta)
        double currentTemp = 23.5;
        double maxTemp = 20.5;

        // Act
        AlertEvaluationResult result = strategy.evaluate(currentTemp, maxTemp, 16);

        // Assert
        assertThat(result.alertTriggered()).isTrue();
        assertThat(result.severity()).isEqualTo("CRITICAL");
        assertThat(result.message()).contains("Excursión térmica en lote cervecero");
        assertThat(result.recommendedAction()).contains("recirculación de glicol");
    }

    @Test
    @DisplayName("Debe disparar alerta HIGH si la temperatura excede levemente el umbral (<= 2°C delta)")
    void shouldTriggerHighAlertWhenExcursionIsModerate() {
        // Arrange: 21.8°C vs umbral máx. de 20.5°C (+1.3°C delta)
        double currentTemp = 21.8;
        double maxTemp = 20.5;

        // Act
        AlertEvaluationResult result = strategy.evaluate(currentTemp, maxTemp, 10);

        // Assert
        assertThat(result.alertTriggered()).isTrue();
        assertThat(result.severity()).isEqualTo("HIGH");
    }

    @Test
    @DisplayName("No debe disparar alerta si la temperatura se mantiene en rango seguro")
    void shouldNotTriggerAlertWhenTemperatureIsSafe() {
        // Arrange: 19.8°C vs umbral máx. de 20.5°C
        double currentTemp = 19.8;
        double maxTemp = 20.5;

        // Act
        AlertEvaluationResult result = strategy.evaluate(currentTemp, maxTemp, 12);

        // Assert
        assertThat(result.alertTriggered()).isFalse();
        assertThat(result.message()).contains("Temperatura de fermentación controlada");
    }
}
