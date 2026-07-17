package com.ravindra.commercex.product.dto.request;

import com.ravindra.commercex.product.enums.ProductStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductRequest(

    @NotBlank(message = "Product name is required.")
    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters.")
    String name,

    @NotBlank(message = "Slug is required.")
    @Size(max = 255, message = "Slug must not exceed 255 characters.")
    String slug,

    @Size(max = 500, message = "Short description must not exceed 500 characters.")
    String shortDescription,

    String description,

    @Size(max = 100, message = "Brand must not exceed 100 characters.")
    String brand,

    @NotBlank(message = "SKU is required.")
    @Size(max = 100, message = "SKU must not exceed 100 characters.")
    String sku,

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.00", inclusive = true, message = "Price cannot be negative.")
    BigDecimal price,

    @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters.")
    String thumbnailUrl,

    boolean featured,

    @NotNull(message = "Product status is required.")
    ProductStatus status,

    @NotNull(message = "Category is required.")
    Long categoryId
) {
}
