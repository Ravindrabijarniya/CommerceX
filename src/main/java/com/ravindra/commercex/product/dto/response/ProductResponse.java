package com.ravindra.commercex.product.dto.response;

import com.ravindra.commercex.product.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(

    Long id,

    String name,

    String slug,

    String shortDescription,

    String description,

    String brand,

    String sku,

    BigDecimal price,

    String thumbnailUrl,

    boolean featured,

    ProductStatus status,

    Long categoryId,

    String categoryName,

    LocalDateTime createdAt,

    LocalDateTime updatedAt
) {
}
