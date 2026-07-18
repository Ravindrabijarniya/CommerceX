package com.ravindra.commercex.cart.exception;

import com.ravindra.commercex.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidCartOperationException extends BusinessException {

    public InvalidCartOperationException(String message) {
        super(message);
    }

}
