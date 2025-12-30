package com.microservicios.clients.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientRegisterRequest (
        @NotBlank(message = "Username is mandatory")
        @Size(min = 4, max = 20, message = "User must have between 4 and 20 characters")
        String username,

        @NotBlank(message = "Password is mandatory")
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
        String password
){}
