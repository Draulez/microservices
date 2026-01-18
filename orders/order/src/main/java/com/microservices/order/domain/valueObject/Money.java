package com.microservices.order.domain.valueObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money
{

	public static final Money ZERO = new Money(BigDecimal.ZERO);

	private final BigDecimal amount;

	public Money(BigDecimal amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Money amount cannot be null");
		}

		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Money amount cannot be negative");
		}

		this.amount = amount.setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal value() {
		return amount;
	}

	public Money add(Money other) {
		requireSameCurrency(other); // futura extensión
		return new Money(this.amount.add(other.amount));
	}

	public Money multiply(int multiplier) {
		if (multiplier < 0) {
			throw new IllegalArgumentException("Multiplier cannot be negative");
		}
		return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)));
	}

	private void requireSameCurrency(Money other) {
		// placeholder si se añade moneda
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Money)) return false;
		Money money = (Money) o;
		return amount.equals(money.amount);
	}

	@Override
	public int hashCode() {
		return amount.hashCode();
	}

	@Override
	public String toString() {
		return amount.toPlainString();
	}
}
