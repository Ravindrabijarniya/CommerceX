package com.ravindra.commercex.payment.provider.dto;


public record PaymentVerificationResult(

    boolean success,

    String providerReference,

    String message

) {
}
