package com.ravindra.commercex.payment.provider.mock;


import com.ravindra.commercex.payment.entity.Payment;
import com.ravindra.commercex.payment.enums.PaymentProviderType;
import com.ravindra.commercex.payment.provider.PaymentProvider;
import com.ravindra.commercex.payment.provider.dto.*;
import org.springframework.stereotype.Component;


import java.util.UUID;


@Component
public class MockPaymentProvider
    implements PaymentProvider {


    @Override
    public PaymentProviderType getProviderType() {
        return PaymentProviderType.MOCK;
    }


    @Override
    public PaymentInitiationResponse initiate(
        Payment payment
    ){

        String reference =
            "MOCK_"
                +
                UUID.randomUUID();


        return new PaymentInitiationResponse(
            reference,
            null,
            null
        );
    }



    @Override
    public PaymentVerificationResult verify(
        Payment payment,
        String signature
    ){

        return new PaymentVerificationResult(
            true,
            payment.getProviderReference(),
            "Mock payment verified"
        );
    }



    @Override
    public PaymentRefundResponse refund(
        Payment payment
    ){

        return new PaymentRefundResponse(
            "REFUND_"
                +
                UUID.randomUUID(),
            true
        );
    }
}
