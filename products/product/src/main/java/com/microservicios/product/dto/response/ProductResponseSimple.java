package com.microservicios.product.dto.response;

import java.math.BigDecimal;

public record ProductResponseSimple (String id,
                                     String name,
                                     BigDecimal price) {
}
