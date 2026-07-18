package com.ravindra.commercex.inventory.exception;

import com.ravindra.commercex.common.exception.ResourceNotFoundException;

public class InventoryNotFoundException extends ResourceNotFoundException {

    public InventoryNotFoundException(Long productId) {
        super("Inventory not found for product id: " + productId);
    }

}
