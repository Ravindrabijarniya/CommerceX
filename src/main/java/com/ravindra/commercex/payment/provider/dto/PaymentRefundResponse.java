package com.ravindra.commercex.payment.provider.dto;


public record PaymentRefundResponse(

    String refundReference,

    boolean success

) {
}
