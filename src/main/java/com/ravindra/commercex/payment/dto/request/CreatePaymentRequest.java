package com.ravindra.commercex.payment.dto.request;


import com.ravindra.commercex.payment.enums.PaymentMethod;
import com.ravindra.commercex.payment.enums.PaymentProviderType;
import jakarta.validation.constraints.NotNull;


public record CreatePaymentRequest(

    @NotNull
    Long orderId,


    @NotNull
    PaymentProviderType provider,


    @NotNull
    PaymentMethod paymentMethod

) {
}
