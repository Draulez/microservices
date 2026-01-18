package com.microservicios.clients.infrastructure.controller.v1;

import com.microservicios.clients.application.ClientService;
import com.microservicios.clients.application.mapper.ClientMapper;
import com.microservicios.clients.domain.Client;
import com.microservicios.clients.domain.Role;
import com.microservicios.clients.dto.request.ClientBlockRequest;
import com.microservicios.clients.dto.request.ClientRegisterRequest;
import com.microservicios.clients.dto.request.ClientUpdateRequest;
import com.microservicios.clients.dto.response.ClientResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/apì/clients/v1")
public class ClientController
{
    private final ClientService clientService;

    ClientController(ClientService clientService)
    {
        this.clientService = clientService;
    }


    //GET METHODS
    /* Usamos @RequestParam para filtros en GET en vez de un DTO porque:
         1. GET no debería tener body, solo query params.
         2. Cada parámetro es explícito y documentable en Swagger/OpenAPI.
         3. Manejar opcionales y defaults es más sencillo.
         4. Evitamos mapeos innecesarios de DTOs para filtros simples.
     */
    @GetMapping("/users_filtered")
    public ResponseEntity<List<ClientResponse>> getUsersFiltered(@RequestParam(required = false) String username,
                                                         @RequestParam(required = false) Role role,
                                                         @RequestParam(required = false) Boolean bloqued,
                                                         @RequestParam(required = false) LocalDate createdAt,
                                                         @RequestParam(required = false) boolean after)
    {
        List<Client> clientList = clientService.getUsersFiltered(username, role, bloqued, createdAt, after);
        List<ClientResponse> response = clientList.stream().map(ClientMapper::toResponse).toList();

        return ResponseEntity.status(HttpStatus.OK).body((response));
    }

    //POST METHODS
    @PostMapping("/new_client")
    public ResponseEntity<ClientResponse> newClient(@Valid @RequestBody ClientRegisterRequest request)
    {
        Client client = clientService.registerNewClient(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ClientMapper.toResponse(client));
    }

    //PUT METHODS
    @PutMapping("/update_user/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateRequest clientUpdateRequest)
    {
        Client client = clientService.updateUserById(id, clientUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    @PutMapping("/block_user")
    public ResponseEntity<Boolean> blockUser(@RequestBody ClientBlockRequest clientBlockRequest)
    {
        Boolean result = clientService.blockUser(clientBlockRequest);
        return  ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
