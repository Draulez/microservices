package com.microservicios.product.dto.response;

import java.math.BigDecimal;

public record ProductResponse(String id,
                              String name,
                              String description,
                              BigDecimal price,
                              String currency,
                              boolean active)
{}
