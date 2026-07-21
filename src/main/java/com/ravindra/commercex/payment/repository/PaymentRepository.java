package com.ravindra.commercex.payment.repository;


import com.ravindra.commercex.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PaymentRepository
    extends JpaRepository<Payment, Long> {


    Optional<Payment> findByPaymentNumber(
        String paymentNumber
    );


    Optional<Payment> findByOrderId(
        Long orderId
    );


    Optional<Payment> findByIdempotencyKey(
        String idempotencyKey
    );


    boolean existsByIdempotencyKey(
        String idempotencyKey
    );
}
