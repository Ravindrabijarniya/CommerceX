package com.ravindra.commercex.product.dto.request;

import com.ravindra.commercex.product.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateProductStatusRequest(

    @NotNull(message = "Product status is required.")
    ProductStatus status
) {
}
