package com.microservices.order.application.mapper;

import com.microservices.order.domain.valueObject.ClientId;
import com.microservices.order.domain.valueObject.Money;
import com.microservices.order.domain.valueObject.OrderId;
import com.microservices.order.domain.valueObject.ProductId;
import com.microservices.order.domain.model.order.Order;
import com.microservices.order.domain.model.order.OrderItem;
import com.microservices.order.infrastructure.persistence.OrderItemJpaEntity;
import com.microservices.order.infrastructure.persistence.OrderJpaEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper
{
	public OrderJpaEntity toEntity(Order order) {
		OrderJpaEntity entity = new OrderJpaEntity();
		entity.setClientId(order.getClientId().value());
		entity.setStatus(order.getStatus());
		entity.setTotalAmount(order.getTotalAmount().value());
		entity.setCreatedAt(order.getCreatedAt());

		List<OrderItemJpaEntity> items = order.getItems().stream()
		                                      .map(item -> {
			                                      OrderItemJpaEntity i = new OrderItemJpaEntity();
			                                      i.setProductId(item.getProductId().value());
			                                      i.setProductName(item.getProductName());
			                                      i.setUnitPrice(item.getUnitPrice().value());
			                                      i.setQuantity(item.getQuantity());
			                                      i.setOrder(entity);
			                                      return i;
		                                      }).toList();

		entity.getItems().addAll(items);
		return entity;
	}

	public Order toDomain(OrderJpaEntity orderJpaEntity)
	{
		List<OrderItem> items = orderJpaEntity.getItems().stream()
		                                      .map(item -> new OrderItem(
				                                      new ProductId(item.getProductId()),
				                                      item.getProductName(),
				                                      new Money(item.getUnitPrice()),
				                                      item.getQuantity()
		                                      )).toList();

		return Order.restore(
				   new OrderId(orderJpaEntity.getId()),
					new ClientId(orderJpaEntity.getClientId()),
					items,
				   new Money(orderJpaEntity.getTotalAmount()),
				   orderJpaEntity.getStatus(),
				   orderJpaEntity.getCreatedAt()
					);
	}
}
