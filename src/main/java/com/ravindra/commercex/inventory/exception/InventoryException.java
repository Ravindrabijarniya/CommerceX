package com.ravindra.commercex.inventory.exception;

import com.ravindra.commercex.common.exception.BusinessException;

public class InventoryException extends BusinessException {

    public InventoryException(String message) {
        super(message);
    }
}
