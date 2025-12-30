package com.microservicios.clients.dto.request;

import com.microservicios.clients.domain.Role;

import java.time.LocalDate;

public record ClientFilteredRequest(
        String username,
        Role role,
        Boolean bloqued,
        LocalDate createdAt,
        boolean after)
{}
