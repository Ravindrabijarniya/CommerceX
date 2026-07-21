package com.ravindra.commercex.notification.event;

import com.ravindra.commercex.notification.enums.NotificationChannel;
import com.ravindra.commercex.notification.enums.NotificationType;
import com.ravindra.commercex.notification.service.NotificationService;
import com.ravindra.commercex.payment.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
@RequiredArgsConstructor
public class PaymentCompletedNotificationListener {

    private final NotificationService notificationService;


    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(PaymentCompletedEvent event) {

        System.out.println(
            "🔥 Notification Listener Triggered: " + event
        );


        notificationService.createNotification(
            event.userId(),
            NotificationType.PAYMENT_SUCCESS,
            NotificationChannel.EMAIL,
            "Payment Successful",
            "Your payment for order "
                + event.orderNumber()
                + " was successful."
        );
    }
}
