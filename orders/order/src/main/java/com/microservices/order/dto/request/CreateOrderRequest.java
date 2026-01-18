package com.microservices.order.dto.request;

import java.util.List;

public record CreateOrderRequest(
		Long clientId,
		List<CreateOrderItemRequest> items
) {}
