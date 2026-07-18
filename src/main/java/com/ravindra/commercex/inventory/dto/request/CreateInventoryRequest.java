package com.ravindra.commercex.inventory.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInventoryRequest {

    @NotNull(message = "Initial stock quantity is required.")
    @Min(value = 0, message = "Initial stock quantity cannot be negative.")
    private Integer stockQuantity;

    @Min(value = 0, message = "Low stock threshold cannot be negative.")
    private Integer lowStockThreshold;
}
