package com.ravindra.commercex.profile.exception;


public class IncorrectPasswordException
    extends RuntimeException {


    public IncorrectPasswordException() {

        super("Current password is incorrect");

    }

}
