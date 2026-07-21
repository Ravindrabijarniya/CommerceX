package com.ravindra.commercex.payment.service;

import com.ravindra.commercex.payment.dto.response.PaymentResponse;
import com.ravindra.commercex.payment.dto.response.PaymentTransactionResponse;
import com.ravindra.commercex.payment.entity.Payment;
import com.ravindra.commercex.payment.exception.PaymentNotFoundException;
import com.ravindra.commercex.payment.mapper.PaymentMapper;
import com.ravindra.commercex.payment.repository.PaymentRepository;
import com.ravindra.commercex.payment.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentQueryService {

    private final PaymentRepository paymentRepository;
    private final PaymentTransactionRepository transactionRepository;
    private final PaymentMapper paymentMapper;

    public PaymentResponse getPayment(Long paymentId) {

        Payment payment =
            paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                    new PaymentNotFoundException(paymentId)
                );

        return paymentMapper.toResponse(payment);
    }

    public List<PaymentTransactionResponse> getTransactions(
        Long paymentId
    ) {

        paymentRepository.findById(paymentId)
            .orElseThrow(() ->
                new PaymentNotFoundException(paymentId));

        return transactionRepository
            .findByPaymentId(paymentId)
            .stream()
            .map(paymentMapper::toResponse)
            .toList();
    }
}
