package com.ravindra.commercex.payment.exception;


public class PaymentAlreadyCompletedException
    extends RuntimeException {


    public PaymentAlreadyCompletedException() {

        super(
            "Payment has already been completed"
        );
    }
}
