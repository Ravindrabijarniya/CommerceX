package com.ravindra.commercex.common.exception;

public abstract class ResourceAlreadyExistsException extends BusinessException {

    protected ResourceAlreadyExistsException(String message) {
        super(message);
    }

}
