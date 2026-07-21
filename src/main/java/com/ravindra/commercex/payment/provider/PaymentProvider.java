package com.ravindra.commercex.payment.provider;


import com.ravindra.commercex.payment.entity.Payment;
import com.ravindra.commercex.payment.enums.PaymentProviderType;
import com.ravindra.commercex.payment.provider.dto.*;


public interface PaymentProvider {

    PaymentProviderType getProviderType();


    PaymentInitiationResponse initiate(
        Payment payment
    );


    PaymentVerificationResult verify(
        Payment payment,
        String signature
    );


    PaymentRefundResponse refund(
        Payment payment
    );


}
