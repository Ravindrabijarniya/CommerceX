package com.ravindra.commercex.catalog.dto;

import java.math.BigDecimal;


public record ProductFilterRequest(

    BigDecimal minPrice,

    BigDecimal maxPrice,

    String category,

    Boolean featured

) {
}
