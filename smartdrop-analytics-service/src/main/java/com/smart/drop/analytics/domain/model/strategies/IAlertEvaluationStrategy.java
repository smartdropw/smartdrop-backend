package com.smart.drop.analytics.domain.model.strategies;

/**
 * Contrato del Patrón Strategy (GoF) para evaluación desacoplada de condiciones de alerta.
 * Permite intercambiar algoritmos de evaluación según el dominio (agua residencial vs cerveza artesanal).
 */
public interface IAlertEvaluationStrategy {

    /**
     * Identificador del tipo de líquido o dominio que atiende la estrategia.
     */
    String getSupportedLiquidType();

    /**
     * Evalúa la lectura física reportada por el sensor y determina si se genera una alerta.
     *
     * @param metricValue   Valor leído (cm/h de descenso o grados Celsius).
     * @param threshold     Umbral límite configurado.
     * @param hourOfDay     Hora del día (0 a 23) para reglas con dependencia temporal.
     * @return AlertEvaluationResult con el diagnóstico y severidad.
     */
    AlertEvaluationResult evaluate(double metricValue, double threshold, int hourOfDay);
}
