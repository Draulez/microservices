package com.microservices.order.application.service;

import com.microservices.order.domain.valueObject.ClientId;
import com.microservices.order.domain.valueObject.Money;
import com.microservices.order.domain.valueObject.OrderId;
import com.microservices.order.domain.valueObject.ProductId;
import com.microservices.order.domain.model.order.Order;
import com.microservices.order.domain.model.order.OrderItem;
import com.microservices.order.domain.model.product.ProductSnapshot;
import com.microservices.order.domain.repository.order.OrderRepository;
import com.microservices.order.application.port.out.ProductCatalogRepository;
import com.microservices.order.dto.request.CreateOrderRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService
{
	private final OrderRepository orderRepository;
	private final ProductCatalogRepository productCatalogRepository;

	public OrderService(OrderRepository orderRepository, ProductCatalogRepository productCatalogRepository)
	{
		this.orderRepository = orderRepository;
		this.productCatalogRepository = productCatalogRepository;
	}


	public OrderId createNewOrder(CreateOrderRequest request)
	{
		List<OrderItem> items = request.items().stream().map(item -> {
			ProductSnapshot product = productCatalogRepository.getProductById(item.productId());

			return new OrderItem(
					new ProductId(product.getId()),
					product.getName(),
					new Money(product.getPrice()),
					item.quantity()
			);
		})
				.toList();

		Order order = new Order(new ClientId(request.clientId()), items);

		orderRepository.save(order);

		return order.getId();
	}


}
