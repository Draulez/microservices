package com.microservices.order.domain.repository.order;

import com.microservices.order.domain.model.order.Order;


public interface OrderRepository
{
	Order save(Order order);
}
