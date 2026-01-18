package com.microservices.order.domain.valueObject;

import java.util.UUID;

public class OrderId
{
	private final Long value;

	public OrderId(Long value) {
		if (value == null) {
			throw new IllegalArgumentException("OrderId cannot be null");
		}
		this.value = value;
	}

	public static OrderId of(Long value) {
		return new OrderId(value);
	}

	public static OrderId newId() {
		return new OrderId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
	}

	public Long value() {
		return value;
	}
}
