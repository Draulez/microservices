package com.microservices.order.infrastructure.rest;

import com.microservices.order.application.port.out.ProductCatalogRepository;
import com.microservices.order.domain.model.product.ProductSnapshot;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductCatalogRestClient implements ProductCatalogRepository {

    private final WebClient webClient;

    public ProductCatalogRestClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    @Override
    public ProductSnapshot getProductById(String productId) {
        return webClient.get()
                .uri("/api/v1/products/{id}", productId)
                .retrieve()
                .bodyToMono(ProductSnapshot.class)
                .block();
    }

}
