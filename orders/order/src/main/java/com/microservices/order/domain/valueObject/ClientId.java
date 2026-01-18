package com.microservices.order.domain.valueObject;
public final class ClientId {

	private final Long value;

	public ClientId(Long value) {
		if (value == null) {
			throw new IllegalArgumentException("ClientId cannot be null");
		}
		this.value = value;
	}

	public Long value() {
		return value;
	}
}

