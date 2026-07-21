package com.ravindra.commercex.payment.exception;


public class DuplicatePaymentException
    extends RuntimeException {


    public DuplicatePaymentException(
        String idempotencyKey
    ) {

        super(
            "Payment already exists for idempotency key: "
                + idempotencyKey
        );
    }
}
