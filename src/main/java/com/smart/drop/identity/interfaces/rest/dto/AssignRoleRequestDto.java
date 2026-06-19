package com.smart.drop.identity.interfaces.rest.dto;

/**
 * Record DTO para solicitudes de asignación de roles.
 */
public record AssignRoleRequestDto(
        Integer userId,
        String roleName
) {
}

