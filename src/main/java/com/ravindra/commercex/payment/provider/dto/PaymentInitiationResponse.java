package com.ravindra.commercex.payment.provider.dto;


public record PaymentInitiationResponse(

    String providerReference,

    String paymentUrl,

    String clientSecret

) {
}
