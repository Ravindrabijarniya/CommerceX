package com.ravindra.commercex.payment.dto.response;


import com.ravindra.commercex.payment.enums.PaymentMethod;
import com.ravindra.commercex.payment.enums.PaymentProviderType;
import com.ravindra.commercex.payment.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record PaymentResponse(

    Long id,

    String paymentNumber,

    Long orderId,

    BigDecimal amount,

    String currency,

    PaymentProviderType provider,

    PaymentMethod paymentMethod,

    PaymentStatus status,

    String providerReference,

    LocalDateTime completedAt

) {
}
