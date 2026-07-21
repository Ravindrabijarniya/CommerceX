package com.ravindra.commercex.payment.repository;


import com.ravindra.commercex.payment.entity.PaymentEvent;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface PaymentEventRepository
    extends JpaRepository<PaymentEvent, Long> {


    List<PaymentEvent> findByPaymentIdOrderByOccurredAtAsc(
        Long paymentId
    );

}
