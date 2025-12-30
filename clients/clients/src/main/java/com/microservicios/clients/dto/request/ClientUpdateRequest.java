package com.microservicios.clients.dto.request;

import com.microservicios.clients.domain.Role;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientUpdateRequest (
        String username,

        @Size(min = 8, message = "Password must have almost 8 characters")
        /*
        (?=.*[A-Z])        -> al menos una mayúscula
        (?=.*\d)           -> al menos un número
        (?=.*[@$!%*?&])    -> al menos un carácter especial
        [A-Za-z\d@$!%*?&]+ -> solo estos caracteres
        */
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Password muest contains almost one capital letter, number and special character"
        )
        String password,
        Role role,
        Boolean bloqued
)
{}
