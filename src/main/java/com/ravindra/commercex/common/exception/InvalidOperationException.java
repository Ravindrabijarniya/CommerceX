package com.ravindra.commercex.common.exception;

public abstract class InvalidOperationException extends BusinessException {

    protected InvalidOperationException(String message) {
        super(message);
    }

}
