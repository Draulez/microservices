package com.microservicios.clients.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClientRepository
{

    void createNewClient(Client client);

    Optional<Client> getClientByUserName(String username);
    Optional<Client> getClientById(Long id);

    List<Client> findByFilter(
            String username,
            Role role,
            Boolean bloqued,
            LocalDate createdAt,
            boolean after
    );
}
