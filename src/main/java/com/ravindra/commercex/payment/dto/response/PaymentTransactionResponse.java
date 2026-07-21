package com.ravindra.commercex.payment.dto.response;


import com.ravindra.commercex.payment.enums.PaymentTransactionStatus;


import java.time.LocalDateTime;


public record PaymentTransactionResponse(

    Long id,

    Integer attemptNumber,

    String providerTransactionId,

    PaymentTransactionStatus status,

    String failureReason,

    LocalDateTime createdAt

) {
}
