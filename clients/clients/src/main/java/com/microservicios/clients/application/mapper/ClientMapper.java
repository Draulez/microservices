package com.microservicios.clients.application.mapper;

import com.microservicios.clients.domain.Client;
import com.microservicios.clients.dto.response.ClientResponse;

public class ClientMapper {

    private ClientMapper() {}

    /* Principalmente actuara de traductor en el controller para las response
       Transformará nuestra entidad en un objeto ClientResponse para que lo
       consideremos una buena práctica */
    public static ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getUsername(),
                client.getRole(),
                client.isBloqued(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}