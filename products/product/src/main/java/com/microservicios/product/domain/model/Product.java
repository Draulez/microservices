package com.microservicios.product.domain.model;

import com.microservicios.product.domain.valueObject.Money;

public class Product
{
    private final String id;
    private final String name;
    private final String description;
    private final Money price;
    private final boolean active;

    public Product(
            String id,
            String name,
            String description,
            Money price,
            boolean active
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Money getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }
}
