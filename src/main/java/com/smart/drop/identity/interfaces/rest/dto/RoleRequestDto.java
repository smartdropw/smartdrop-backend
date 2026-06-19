package com.smart.drop.identity.interfaces.rest.dto;

/**
 * Record DTO para solicitudes de creación/actualización de roles.
 */
public record RoleRequestDto(
        String name,
        String description
) {
}

