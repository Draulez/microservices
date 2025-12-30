package com.microservicios.clients.dto.response;

import com.microservicios.clients.domain.Role;

import java.time.LocalDateTime;

public record ClientResponse(
        Long id,
        String username,
        Role role,
        boolean blocked,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
