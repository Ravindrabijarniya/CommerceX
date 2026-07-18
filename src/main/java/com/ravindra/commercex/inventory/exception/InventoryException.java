package com.ravindra.commercex.inventory.exception;

import com.ravindra.commercex.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InventoryException extends BusinessException {

    public InventoryException(String message) {
        super(message);
    }
}
