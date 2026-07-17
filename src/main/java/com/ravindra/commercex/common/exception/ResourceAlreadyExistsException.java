package com.ravindra.commercex.common.exception;

public abstract class ResourceAlreadyExistsException extends RuntimeException {

    protected ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
