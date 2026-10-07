package com.smart.drop.analytics.domain.model.strategies;

import org.springframework.stereotype.Component;

/**
 * Estrategia concreta de evaluación: Detección de Fugas Nocturnas en Tanques de Agua Potable.
 * Aplica para el segmento residencial (US05). Evalúa el consumo durante horas de reposo (01:00 AM a 05:00 AM).
 * Si hay un descenso volumétrico continuo superior al umbral (típico > 5.0 cm/h), se clasifica como fuga crítica.
 */
@Component
public class NightLeakDetectionStrategy implements IAlertEvaluationStrategy {

    public static final String LIQUID_TYPE = "WATER";

    @Override
    public String getSupportedLiquidType() {
        return LIQUID_TYPE;
    }

    @Override
    public AlertEvaluationResult evaluate(double dropRateCmPerHour, double thresholdCmPerHour, int hourOfDay) {
        boolean isNightHours = (hourOfDay >= 1 && hourOfDay <= 5);

        if (!isNightHours) {
            return AlertEvaluationResult.noAlert(
                    "NightLeakDetectionStrategy",
                    "Horario diurno (" + hourOfDay + ":00h): el consumo observado se clasifica como uso humano regular."
            );
        }

        if (dropRateCmPerHour > thresholdCmPerHour) {
            double excess = dropRateCmPerHour - thresholdCmPerHour;
            return AlertEvaluationResult.triggered(
                    "NightLeakDetectionStrategy",
                    "CRITICAL",
                    String.format("Fuga nocturna detectada a las %02d:00h: descenso de %.2f cm/h supera el umbral límite de %.2f cm/h (+%.2f cm/h).",
                            hourOfDay, dropRateCmPerHour, thresholdCmPerHour, excess),
                    "Cerrar inmediatamente la válvula matriz de paso e inspeccionar tuberías empotradas y flotadores de inodoros."
            );
        }

        return AlertEvaluationResult.noAlert(
                "NightLeakDetectionStrategy",
                String.format("Monitoreo nocturno estable (%02d:00h): descenso de %.2f cm/h dentro del margen admisible.",
                        hourOfDay, dropRateCmPerHour)
        );
    }
}
