package com.ravindra.commercex.payment.exception;


public class PaymentNotAllowedException
    extends RuntimeException {


    public PaymentNotAllowedException(
        String message
    ) {

        super(message);
    }
}
