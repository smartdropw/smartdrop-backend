package com.smart.drop.analytics.domain.model.strategies;

import org.springframework.stereotype.Component;

/**
 * Estrategia concreta de evaluación: Detección Temprana de Excursiones Térmicas en Cerveza Artesanal.
 * Aplica para el segmento PYME de bebidas (US07). Evalúa la temperatura del mosto en fermentación.
 * Si la temperatura excede el umbral seguro (por ejemplo > 21.2°C en cepas Ale), se cataloga como riesgo biológico.
 */
@Component
public class ThermalExcursionStrategy implements IAlertEvaluationStrategy {

    public static final String LIQUID_TYPE = "BEER_WORT";

    @Override
    public String getSupportedLiquidType() {
        return LIQUID_TYPE;
    }

    @Override
    public AlertEvaluationResult evaluate(double temperatureCelsius, double maxAllowedTemperature, int hourOfDay) {
        if (temperatureCelsius > maxAllowedTemperature) {
            double delta = temperatureCelsius - maxAllowedTemperature;
            String severity = delta > 2.0 ? "CRITICAL" : "HIGH";

            return AlertEvaluationResult.triggered(
                    "ThermalExcursionStrategy",
                    severity,
                    String.format("Excursión térmica en lote cervecero: temperatura actual de %.2f°C supera el umbral crítico de %.2f°C (+%.2f°C).",
                            temperatureCelsius, maxAllowedTemperature, delta),
                    "Activar recirculación de glicol en la camisa de enfriamiento y verificar sensor PT100 del fermentador."
            );
        }

        return AlertEvaluationResult.noAlert(
                "ThermalExcursionStrategy",
                String.format("Temperatura de fermentación controlada: %.2f°C (dentro de tolerancia segura <= %.2f°C).",
                        temperatureCelsius, maxAllowedTemperature)
        );
    }
}
