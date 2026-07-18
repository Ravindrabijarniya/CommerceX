package com.ravindra.commercex.order.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(

    @NotNull(message = "Shipping address id is required.")
    Long shippingAddressId

) {
}
