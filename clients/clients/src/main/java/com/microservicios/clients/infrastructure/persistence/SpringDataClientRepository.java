package com.microservicios.clients.infrastructure.persistence;

import com.microservicios.clients.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpringDataClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.username = :username")
    Optional<Client> getUserByUserName(@Param("username") String username);

}
