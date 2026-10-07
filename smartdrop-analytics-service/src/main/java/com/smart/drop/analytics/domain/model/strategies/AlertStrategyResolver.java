package com.smart.drop.analytics.domain.model.strategies;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Resolver/Context del Patrón Strategy.
 * Inyecta todas las implementaciones disponibles de IAlertEvaluationStrategy mediante Spring
 * y despacha dinámicamente la estrategia correcta según el tipo de líquido a evaluar.
 */
@Service
public class AlertStrategyResolver {

    private final Map<String, IAlertEvaluationStrategy> strategies;

    public AlertStrategyResolver(List<IAlertEvaluationStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getSupportedLiquidType().toUpperCase(),
                        Function.identity()
                ));
    }

    /**
     * Resuelve y ejecuta la estrategia adecuada para el líquido indicado.
     *
     * @param liquidType Tipo de líquido ('WATER', 'BEER_WORT', etc.)
     * @param metricValue Valor físico medido
     * @param threshold Umbral de alerta
     * @param hourOfDay Hora de ocurrencia (0-23)
     * @return AlertEvaluationResult
     */
    public AlertEvaluationResult evaluateMetric(String liquidType, double metricValue, double threshold, int hourOfDay) {
        String key = (liquidType != null) ? liquidType.toUpperCase() : "WATER";
        IAlertEvaluationStrategy strategy = strategies.get(key);

        if (strategy == null) {
            return AlertEvaluationResult.noAlert(
                    "DefaultFallback",
                    "No se encontró una estrategia especializada para el líquido: " + liquidType + ". Evaluación omitida."
            );
        }

        return strategy.evaluate(metricValue, threshold, hourOfDay);
    }

    public boolean hasStrategy(String liquidType) {
        return liquidType != null && strategies.containsKey(liquidType.toUpperCase());
    }
}
