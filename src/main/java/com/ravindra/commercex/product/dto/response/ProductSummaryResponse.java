package com.ravindra.commercex.product.dto.response;

import java.math.BigDecimal;

public record ProductSummaryResponse(

    Long id,

    String name,

    String slug,

    String brand,

    BigDecimal price,

    String thumbnailUrl,

    boolean featured
) {
}
