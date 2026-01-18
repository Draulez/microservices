package com.microservices.order.infrastructure.controller.v1;

import com.microservices.order.application.service.OrderService;
import com.microservices.order.domain.valueObject.OrderId;
import com.microservices.order.dto.request.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders/v1")
public class OrderController
{
	private final OrderService orderService;

	OrderController(OrderService orderService)
	{
		this.orderService = orderService;
	}

	@PostMapping("/new_order")
	public ResponseEntity<OrderId> newOrder(@RequestBody CreateOrderRequest request)
	{
		OrderId orderid = orderService.createNewOrder(request);

		return ResponseEntity.status(HttpStatus.OK).body((orderid));
	}
}
