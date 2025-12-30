package com.microservicios.clients.application;

import com.microservicios.clients.domain.Client;
import com.microservicios.clients.domain.ClientRepository;
import com.microservicios.clients.domain.Role;
import com.microservicios.clients.dto.request.ClientBlockRequest;
import com.microservicios.clients.dto.request.ClientRegisterRequest;
import com.microservicios.clients.dto.request.ClientUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder)
    {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Client registerNewClient(ClientRegisterRequest clientRegisterRequest)
    {
        if (clientRepository.getClientByUserName(clientRegisterRequest.username()).isPresent())
        {
            throw new ClientAlreadyExistsException(clientRegisterRequest.username());
        }

        String hashedPassword = passwordEncoder.encode(clientRegisterRequest.password());

        Client client = new Client(clientRegisterRequest.username(), hashedPassword);

        clientRepository.createNewClient(client);

        return client;

    }

    @Transactional
    public Client updateUserById(Long id, ClientUpdateRequest clientUpdateRequest)
    {
        Client user = clientRepository.getClientById(id).orElseThrow(() -> new ClientNotFoundException(id));

        boolean modified = false;

        if (clientUpdateRequest.username() != null)
        {

            clientRepository.getClientByUserName(clientUpdateRequest.username())
                    .filter(existing -> !(existing.getId().equals(id)))
                    .ifPresent(existing -> {
                        throw new ClientAlreadyExistsException(clientUpdateRequest.username());
                    });

            user.setUsername(clientUpdateRequest.username());
            modified = true;
        }

        if (clientUpdateRequest.password() != null)
        {
            String hashedPassword = passwordEncoder.encode(clientUpdateRequest.password());
            user.setPassword(hashedPassword);
            modified = true;
        }

        if (clientUpdateRequest.bloqued() != null)
        {
            user.setBloqued(clientUpdateRequest.bloqued());
            modified = true;
        }

        if (clientUpdateRequest.role() != null)
        {
            user.setRole(clientUpdateRequest.role());
            modified = true;
        }

        if (modified)
        {
            user.setUpdatedAt(LocalDateTime.now());

            return user;
        }
        else
        {
            throw new NoChangesDetectedException();
        }
    }

    @Transactional(readOnly = true)
    public List<Client> getUsersFiltered(String username, Role role, Boolean bloqued, LocalDate createdAt, boolean after)
    {
        return clientRepository.findByFilter(username, role, bloqued, createdAt, after);
    }

    @Transactional
    public boolean blockUser(ClientBlockRequest userBlockRequest)
    {

        if (userBlockRequest.id() != null)
        {
            Client user = clientRepository.getClientById(userBlockRequest.id()).orElseThrow(() -> new ClientNotFoundException(userBlockRequest.id()));
            user.setBloqued(true);
        }
        else if (userBlockRequest.username() != null)
        {
            Client user = clientRepository.getClientByUserName(userBlockRequest.username()).orElseThrow(() -> new ClientNotFoundException(userBlockRequest.username()));
            user.setBloqued(true);
        }
        else
        {
            throw new NoParamsMandatoryFound(List.of("id", "username"));
        }
        return true;
    }


    public static class ClientNotFoundException extends RuntimeException
    {
        public ClientNotFoundException(Object id)
        {
            super("Client with id " + id + " not found.");
        }
    }

    public static class ClientAlreadyExistsException extends RuntimeException
    {
        public ClientAlreadyExistsException(String username)
        {
            super("Username " + username + " already exists");
        }
    }

    public static class NoChangesDetectedException extends RuntimeException
    {
        public NoChangesDetectedException() {super("User was not modified");}
    }

    public static class NoParamsMandatoryFound extends RuntimeException
    {
        public NoParamsMandatoryFound(List<String> params) {super(buildMessage(params));}
    }

    private static String buildMessage(List<String> params)
    {
        if (params == null || params.isEmpty())
        {
            return "Mandatory parameters were not found";
        }
        return "The following mandatory params were not found: " +
                String.join(", ", params);
    }

    public static class NoParamMandatoryFound extends RuntimeException
    {
        public NoParamMandatoryFound(String param) {super(buildMessage(param));}
    }

    private static String buildMessage(String param)
    {
        if (param == null || param.isEmpty())
        {
            return "Mandatory parameters were not found";
        }
        return "The following mandatory params were not found: " + param;
    }
}
