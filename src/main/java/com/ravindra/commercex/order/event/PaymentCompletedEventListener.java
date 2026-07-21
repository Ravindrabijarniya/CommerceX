package com.ravindra.commercex.order.event;


import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.order.repository.OrderRepository;
import com.ravindra.commercex.payment.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
@RequiredArgsConstructor
public class PaymentCompletedEventListener {


    private final OrderRepository orderRepository;



    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    @Transactional(
        propagation = Propagation.REQUIRES_NEW
    )
    public void handlePaymentCompleted(
        PaymentCompletedEvent event
    ){


        Order order =
            orderRepository.findById(
                    event.orderId()
                )
                .orElseThrow();



        order.markAsPaid();

    }
}
