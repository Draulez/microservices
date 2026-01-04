package com.microservicios.product.application.mapper;

import com.microservicios.product.domain.model.Product;
import com.microservicios.product.domain.valueObject.Money;
import com.microservicios.product.infrastructure.persistence.mongo.ProductDocument;
import org.springframework.stereotype.Component;

@Component
public class ProductMongoMapper {

    public ProductDocument toDocument(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId());
        doc.setName(product.getName());
        doc.setDescription(product.getDescription());
        doc.setPriceAmount(product.getPrice().getAmount());
        doc.setPriceCurrency(product.getPrice().getCurrency());
        doc.setActive(product.isActive());
        return doc;
    }

    public Product toDomain(ProductDocument doc) {
        return new Product(
                doc.getId(),
                doc.getName(),
                doc.getDescription(),
                new Money(doc.getPriceAmount(), doc.getPriceCurrency()),
                doc.isActive()
        );
    }
}

