package com.ravindra.commercex.address.exception;

import com.ravindra.commercex.common.exception.InvalidOperationException;

public class InvalidAddressException extends InvalidOperationException {

    public InvalidAddressException(String message) {
        super(message);
    }

}
