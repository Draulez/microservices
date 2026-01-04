package com.microservicios.product.infrastructure.persistence.mongo;

import com.microservicios.product.application.mapper.ProductMongoMapper;
import com.microservicios.product.domain.model.Product;
import com.microservicios.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoProductRepository implements ProductRepository
{

    private final SpringDataProductRepository repository;
    private final ProductMongoMapper mapper;

    public MongoProductRepository(
            SpringDataProductRepository repository,
            ProductMongoMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Product save(Product product) {
        ProductDocument saved =
                repository.save(mapper.toDocument(product));
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Product> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}

