package com.ravindra.commercex.payment.service;

import com.ravindra.commercex.payment.entity.Payment;
import com.ravindra.commercex.payment.entity.PaymentEvent;
import com.ravindra.commercex.payment.enums.PaymentEventType;
import com.ravindra.commercex.payment.event.PaymentCompletedEvent;
import com.ravindra.commercex.payment.exception.PaymentNotFoundException;
import com.ravindra.commercex.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentWebhookService {

    private final PaymentRepository paymentRepository;

    private final PaymentEventPublisher paymentEventPublisher;

    public void handleSuccessfulPayment(
        String paymentNumber,
        String providerReference,
        String signature
    ) {

        Payment payment = paymentRepository
            .findByPaymentNumber(paymentNumber)
            .orElseThrow(() ->
                new PaymentNotFoundException(paymentNumber));

        payment.markSuccess(providerReference);

        payment.addEvent(
            PaymentEvent.create(
                PaymentEventType.PAYMENT_SUCCESS,
                "Payment confirmed by provider",
                null
            )
        );

        paymentEventPublisher.publishPaymentCompleted(

            new PaymentCompletedEvent(

                payment.getId(),

                payment.getOrder().getId(),

                payment.getAmount(),

                providerReference
            )
        );
    }
}
