package com.smart.drop.inventory.domain.model.entities;

import java.util.Set;

/**
 * Immutable domain entity for water storage tanks.
 */
public record Tank(
        Integer tankId,
        Integer userId,
        String name,
        Integer capacity,
        Integer current,
        String liquidType
) {
    private static final Set<String> VALID_LIQUID_TYPES = Set.of(
            "WATER", "INDUSTRIAL_WATER", "FUEL", "CHEMICAL", "DAIRY", "OTHER"
    );

    public Tank {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Tank name cannot be null or blank");
        }
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (capacity == null || capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        if (current != null && (current < 0 || current > capacity)) {
            throw new IllegalArgumentException("Current level must be between 0 and capacity");
        }
        if (liquidType != null && !VALID_LIQUID_TYPES.contains(liquidType)) {
            throw new IllegalArgumentException("Invalid liquid type: " + liquidType + ". Valid values: " + VALID_LIQUID_TYPES);
        }
    }

    public static Tank create(Integer userId, String name, Integer capacity, Integer current, String liquidType) {
        String resolvedLiquidType = (liquidType != null && !liquidType.isBlank()) ? liquidType : "WATER";
        return new Tank(null, userId, name, capacity, current, resolvedLiquidType);
    }
}
