package com.smart.drop.identity.interfaces.rest.dto;

/**
 * Record DTO para respuestas de usuario.
 */
public record UserResponseDto(
        Integer userId,
        String fullName,
        String email,
        java.time.LocalDateTime createdAt
) {
}

