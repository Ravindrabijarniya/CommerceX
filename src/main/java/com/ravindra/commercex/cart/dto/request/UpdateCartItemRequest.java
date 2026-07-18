package com.ravindra.commercex.cart.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCartItemRequest {

    @Min(value = 0, message = "Quantity cannot be negative.")
    private int quantity;

}
