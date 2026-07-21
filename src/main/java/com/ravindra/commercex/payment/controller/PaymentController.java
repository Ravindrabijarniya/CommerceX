package com.ravindra.commercex.payment.controller;

import com.ravindra.commercex.payment.dto.request.CreatePaymentRequest;
import com.ravindra.commercex.payment.dto.response.PaymentResponse;
import com.ravindra.commercex.payment.dto.response.PaymentTransactionResponse;
import com.ravindra.commercex.payment.service.PaymentQueryService;
import com.ravindra.commercex.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final PaymentQueryService paymentQueryService;


    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(

        @RequestHeader("Idempotency-Key")
            @NonNull
        String idempotencyKey,

        @Valid
        @RequestBody
        CreatePaymentRequest request
    ) {

        PaymentResponse response =
            paymentService.createPayment(
                request,
                idempotencyKey
            );

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(
        @PathVariable @NonNull Long paymentId
    ) {

        return ResponseEntity.ok(
            paymentQueryService.getPayment(paymentId)
        );
    }

    @GetMapping("/{paymentId}/transactions")
    public ResponseEntity<List<PaymentTransactionResponse>>
    getTransactions(
        @PathVariable Long paymentId
    ) {

        return ResponseEntity.ok(
            paymentQueryService.getTransactions(paymentId)
        );
    }
}
