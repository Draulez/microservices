package com.microservicios.product.infrastructure.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataProductRepository
        extends MongoRepository<ProductDocument, String> {
}

