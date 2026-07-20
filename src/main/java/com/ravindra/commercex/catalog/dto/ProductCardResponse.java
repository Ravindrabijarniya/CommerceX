package com.ravindra.commercex.catalog.dto;

import java.math.BigDecimal;

public record ProductCardResponse(

    Long id,

    String name,

    String slug,

    BigDecimal price,

    boolean featured,

    boolean available,

    String categoryName

) {
}
