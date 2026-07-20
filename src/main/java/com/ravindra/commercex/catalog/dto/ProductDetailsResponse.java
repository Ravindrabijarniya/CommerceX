package com.ravindra.commercex.catalog.dto;

import java.math.BigDecimal;

public record ProductDetailsResponse(

    Long id,

    String name,

    String slug,

    String description,

    BigDecimal price,

    boolean featured,

    boolean available,

    String categoryName

) {
}
