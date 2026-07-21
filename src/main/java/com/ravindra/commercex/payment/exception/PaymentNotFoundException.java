package com.ravindra.commercex.payment.exception;


public class PaymentNotFoundException extends RuntimeException {


    public PaymentNotFoundException(
        Long paymentId
    ) {

        super(
            "Payment not found with id: "
                + paymentId
        );
    }


    public PaymentNotFoundException(
        String paymentNumber
    ) {

        super(
            "Payment not found with payment number: "
                + paymentNumber
        );
    }
}
