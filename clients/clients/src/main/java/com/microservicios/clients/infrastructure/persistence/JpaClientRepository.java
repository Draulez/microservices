package com.microservicios.clients.infrastructure.persistence;

import com.microservicios.clients.domain.Client;
import com.microservicios.clients.domain.ClientRepository;
import com.microservicios.clients.domain.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JpaClientRepository implements ClientRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final SpringDataClientRepository jpa;

    public JpaClientRepository(SpringDataClientRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void createNewClient(Client client)
    {
        jpa.save(client);
    }

    @Override
    public Optional<Client> getClientByUserName(String username)
    {
        return jpa.getUserByUserName(username);
    }

    @Override
    public Optional<Client> getClientById(Long id)
    {
        return jpa.findById(id);
    }

    @Override
    public List<Client> findByFilter(String username, Role role, Boolean bloqued, LocalDate createdAt, boolean after)
    {
        String sql = """
            SELECT * FROM clients WHERE 1=1
        """;

        Map<String, Object> params = new HashMap<>();

        if (username != null) {
            sql += " AND username = :username";
            params.put("username", username);
        }

        if (role != null) {
            sql += " AND role = :role";
            params.put("role", role.name());
        }

        if (bloqued != null) {
            sql += " AND blocked = :blocked";
            params.put("blocked", bloqued);
        }

        if (createdAt != null) {
            sql += after
                    ? " AND created_at >= :createdAt"
                    : " AND created_at <= :createdAt";
            params.put("createdAt", createdAt);
        }

        Query query = entityManager.createNativeQuery(sql, Client.class);
        params.forEach(query::setParameter);

        return query.getResultList();

    }

}
