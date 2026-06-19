package com.smart.drop.support.interfaces.rest.dto;

import java.time.LocalDateTime;

public record SupportTicketResponse(
        Integer ticketId,
        Integer userId,
        String description,
        String status,
        LocalDateTime createdAt
) {
}

