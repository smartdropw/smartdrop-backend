package com.smart.drop.identity.interfaces.rest.dto;

/**
 * Record DTO para respuestas de roles.
 */
public record RoleResponseDto(
        Integer roleId,
        String name,
        String description
) {
}

