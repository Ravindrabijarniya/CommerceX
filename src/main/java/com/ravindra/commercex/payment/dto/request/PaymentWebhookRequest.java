package com.ravindra.commercex.payment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PaymentWebhookRequest(

    @NotBlank
    String paymentNumber,

    @NotBlank
    String providerReference,

    @NotBlank
    String signature

) {
}
