package com.microservicios.product.infrastructure.controller.v1;

import com.microservicios.product.application.service.ProductService;
import com.microservicios.product.dto.request.CreateProductRequest;
import com.microservicios.product.dto.response.ProductResponse;
import com.microservicios.product.dto.response.ProductResponseSimple;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")

public class ProductController
{
    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService = productService;
    }

    // POST METHODS

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@RequestBody CreateProductRequest request)
    {
        return productService.create(request);
    }

    // GET METHODS

    @GetMapping("/{id}")
    public ProductResponseSimple getById(@PathVariable String id)
    {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductResponse> getAll()
    {
        return productService.getAll();
    }

    // DELETE METHODS

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id)
    {
        productService.delete(id);
    }



}
