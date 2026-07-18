package com.ravindra.commercex.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {

    protected BusinessException(String message, HttpStatus badRequest) {
        super(message);
    }
}
