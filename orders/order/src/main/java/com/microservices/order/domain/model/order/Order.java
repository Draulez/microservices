package com.microservices.order.domain.model.order;

import com.microservices.order.domain.valueObject.ClientId;
import com.microservices.order.domain.valueObject.Money;
import com.microservices.order.domain.valueObject.OrderId;

import java.time.LocalDateTime;
import java.util.List;

public class Order
{
	private OrderId id;
	private final ClientId clientId;
	private final List<OrderItem> items;
	private OrderStatus status;
	private final Money totalAmount;
	private final LocalDateTime createdAt;



	public Order(
			ClientId clientId,
			List<OrderItem> items
	) {
		if (items == null || items.isEmpty()) {
			throw new IllegalArgumentException("Order must have at least one item");
		}

		this.clientId = clientId;
		this.items = List.copyOf(items);
		this.totalAmount = calculateTotal(items);
		this.status = OrderStatus.CREATED;
		this.createdAt = LocalDateTime.now();
	}

	private Order(OrderId id,
			      ClientId clientId,
	              List<OrderItem> items,
	              Money totalAmount,
	              OrderStatus status,
	              LocalDateTime createdAt)
	{
		this.id = id;
		this.clientId = clientId;
		this.items = List.copyOf(items);
		this.totalAmount = totalAmount;
		this.status = status;
		this.createdAt = createdAt;
	}

	public static Order restore(OrderId id,
	                             ClientId clientId,
	                             List<OrderItem> items,
	                             Money totalAmount,
	                             OrderStatus status,
	                             LocalDateTime createdAt)
	{
		return new Order(id, clientId, items, totalAmount, status, createdAt);
	}

	public OrderId getId()
	{
		return id;
	}

	public ClientId getClientId()
	{
		return clientId;
	}

	public List<OrderItem> getItems()
	{
		return items;
	}

	public OrderStatus getStatus()
	{
		return status;
	}

	public void setStatus(OrderStatus status)
	{
		this.status = status;
	}

	public Money getTotalAmount()
	{
		return totalAmount;
	}

	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	private Money calculateTotal(List<OrderItem> items) {
		return items.stream()
		            .map(OrderItem::subtotal)
		            .reduce(Money.ZERO, Money::add);
	}
}
