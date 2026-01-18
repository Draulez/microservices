package com.microservices.order.dto.request;

public record CreateOrderItemRequest(
		String productId,
		int quantity
)
{}
