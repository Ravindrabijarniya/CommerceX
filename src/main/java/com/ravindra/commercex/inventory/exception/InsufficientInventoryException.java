package com.ravindra.commercex.inventory.exception;

import com.ravindra.commercex.common.exception.InvalidOperationException;

public class InsufficientInventoryException extends InvalidOperationException {

    public InsufficientInventoryException() {
        super("Insufficient inventory available.");
    }
}
