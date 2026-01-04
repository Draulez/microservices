package com.microservicios.product.application.service;

import com.microservicios.product.application.exception.ProductNotFoundException;
import com.microservicios.product.domain.model.Product;
import com.microservicios.product.domain.repository.ProductRepository;
import com.microservicios.product.domain.valueObject.Money;
import com.microservicios.product.dto.request.CreateProductRequest;
import com.microservicios.product.dto.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService
{
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public ProductResponse create(CreateProductRequest request)
    {
        Product product = new Product(UUID.randomUUID().toString(),
                request.name(),
                request.description(),
                new Money(request.price(), request.currency()),
                true);

        Product saved = productRepository.save(product);

        return toResponse(product);
    }

    public ProductResponse getById(String id)
    {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return toResponse(product);
    }

    public List<ProductResponse> getAll()
    {
        return  productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public void delete(String id)
    {
        if (false == productRepository.findById(id).isPresent())
        {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    private ProductResponse toResponse(Product product)
    {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().getAmount(),
                product.getPrice().getCurrency(),
                product.isActive()
        );
    }
}
