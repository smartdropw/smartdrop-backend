package com.smart.drop.identity.interfaces.rest.dto;

/**
 * Record DTO para solicitudes de registro de usuario.
 */
public record RegisterUserRequestDto(
        String fullName,
        String email,
        String password
) {
}

