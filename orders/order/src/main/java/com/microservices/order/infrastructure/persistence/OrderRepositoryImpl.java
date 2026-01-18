package com.microservices.order.infrastructure.persistence;

import com.microservices.order.application.mapper.OrderMapper;
import com.microservices.order.domain.model.order.Order;
import com.microservices.order.domain.repository.order.OrderRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderRepositoryImpl implements OrderRepository
{
	private final SpringDataOrderRepository jpa;
	private final OrderMapper mapper;

	public OrderRepositoryImpl(SpringDataOrderRepository jpa, OrderMapper mapper) {
		this.jpa = jpa;
		this.mapper = mapper;
	}

	@Override
	public Order save(Order order)
	{
		OrderJpaEntity entity = mapper.toEntity(order);
		jpa.save(entity);
		return order;
	}
}
