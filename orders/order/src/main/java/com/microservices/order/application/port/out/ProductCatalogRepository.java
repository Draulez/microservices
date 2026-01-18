package com.microservices.order.application.port.out;

import com.microservices.order.domain.model.product.ProductSnapshot;

public interface ProductCatalogRepository
{
	ProductSnapshot getProductById(String productId);
}
