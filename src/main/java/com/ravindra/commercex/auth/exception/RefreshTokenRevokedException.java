package com.ravindra.commercex.auth.exception;

public class RefreshTokenRevokedException extends RuntimeException {

    public RefreshTokenRevokedException(String message) {
        super(message);
    }

}
