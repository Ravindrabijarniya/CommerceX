package com.ravindra.commercex.payment.event;


import java.math.BigDecimal;


public record PaymentCompletedEvent(

    Long paymentId,

    Long orderId,

    BigDecimal amount,

    String providerReference

) {
}
