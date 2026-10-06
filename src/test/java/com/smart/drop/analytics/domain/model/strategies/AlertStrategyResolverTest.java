package com.smart.drop.analytics.domain.model.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Pruebas Unitarias - Patrón Strategy: Resolver y Despacho Polimórfico")
class AlertStrategyResolverTest {

    private AlertStrategyResolver resolver;

    @BeforeEach
    void setUp() {
        List<IAlertEvaluationStrategy> strategies = List.of(
                new NightLeakDetectionStrategy(),
                new ThermalExcursionStrategy()
        );
        resolver = new AlertStrategyResolver(strategies);
    }

    @Test
    @DisplayName("Debe resolver correctamente la estrategia para agua (WATER)")
    void shouldResolveWaterStrategy() {
        AlertEvaluationResult result = resolver.evaluateMetric("WATER", 10.0, 4.0, 2);
        assertThat(result.strategyName()).isEqualTo("NightLeakDetectionStrategy");
        assertThat(result.alertTriggered()).isTrue();
    }

    @Test
    @DisplayName("Debe resolver correctamente la estrategia para cerveza (BEER_WORT)")
    void shouldResolveBeerWortStrategy() {
        AlertEvaluationResult result = resolver.evaluateMetric("BEER_WORT", 24.0, 20.0, 15);
        assertThat(result.strategyName()).isEqualTo("ThermalExcursionStrategy");
        assertThat(result.alertTriggered()).isTrue();
    }

    @Test
    @DisplayName("Debe utilizar fallback seguro si el tipo de líquido no cuenta con estrategia")
    void shouldHandleUnknownLiquidTypeGracefully() {
        AlertEvaluationResult result = resolver.evaluateMetric("UNKNOWN_CHEMICAL", 50.0, 10.0, 8);
        assertThat(result.strategyName()).isEqualTo("DefaultFallback");
        assertThat(result.alertTriggered()).isFalse();
    }
}
