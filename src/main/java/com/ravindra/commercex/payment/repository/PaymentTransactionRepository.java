package com.ravindra.commercex.payment.repository;


import com.ravindra.commercex.payment.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface PaymentTransactionRepository
    extends JpaRepository<PaymentTransaction, Long> {


    List<PaymentTransaction> findByPaymentId(
        Long paymentId
    );

}
