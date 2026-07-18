package com.ravindra.commercex.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCartItemRequest {

    @NotNull(message = "Product id is required.")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

}
