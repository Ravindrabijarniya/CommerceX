package com.ravindra.commercex.payment.exception;

import com.ravindra.commercex.payment.enums.PaymentProviderType;

public class UnsupportedPaymentProviderException
    extends RuntimeException {

    public UnsupportedPaymentProviderException(
        PaymentProviderType providerType
    ) {

        super("Unsupported payment provider: " + providerType);
    }
}
