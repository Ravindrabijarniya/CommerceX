package com.ravindra.commercex.payment.exception;


import com.ravindra.commercex.payment.enums.PaymentStatus;


public class InvalidPaymentStateException
    extends RuntimeException {


    public InvalidPaymentStateException(
        PaymentStatus current,
        PaymentStatus expected
    ) {

        super(
            "Invalid payment state transition. Current state: "
                + current +
                ", expected: "
                + expected
        );
    }
}
