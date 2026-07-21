package com.ravindra.commercex.payment.controller;

import com.ravindra.commercex.payment.dto.request.PaymentWebhookRequest;
import com.ravindra.commercex.payment.service.PaymentWebhookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment-webhooks")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentWebhookService paymentWebhookService;

    @PostMapping("/mock")
    public ResponseEntity<Void> handleMockWebhook(

        @Valid
        @RequestBody
        PaymentWebhookRequest request
    ) {

        paymentWebhookService.handleSuccessfulPayment(

            request.paymentNumber(),
            request.providerReference(),
            request.signature()

        );

        return ResponseEntity.ok().build();
    }
}
