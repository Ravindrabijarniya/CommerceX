package com.ravindra.commercex.payment.service;


import com.ravindra.commercex.payment.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {


    private final ApplicationEventPublisher publisher;



    public void publishPaymentCompleted(
        PaymentCompletedEvent event
    ){

        publisher.publishEvent(event);

    }
}
