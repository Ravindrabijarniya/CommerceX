package com.ravindra.commercex.product.exception;

import com.ravindra.commercex.common.exception.ResourceAlreadyExistsException;

public class ProductAlreadyExistsException extends ResourceAlreadyExistsException {

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
