package com.microservices.order.domain.valueObject;

public class ProductId
{
	private final String value;

	public ProductId(String value) {
		if (value == null) {
			throw new IllegalArgumentException("ProductId cannot be null");
		}
		this.value = value;
	}

	public static ProductId of(String value) {
		return new ProductId(value);
	}


	public String value() {
		return value;
	}
}
