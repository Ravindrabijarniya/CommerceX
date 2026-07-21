package com.ravindra.commercex.payment.validator;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.order.entity.OrderStatus;
import com.ravindra.commercex.order.exception.OrderNotFoundException;
import com.ravindra.commercex.order.repository.OrderRepository;
import com.ravindra.commercex.payment.exception.PaymentNotAllowedException;
import com.ravindra.commercex.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentBusinessValidator {

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    public Order validatePaymentCreation(
        Long orderId,
        User user
    ) {

        Order order = orderRepository.findById(orderId)
            .orElseThrow(() ->
                new OrderNotFoundException(orderId)
            );

        validateOwnership(order, user);

        validateOrderStatus(order);

        validateDuplicatePayment(order.getId());

        return order;
    }

    private void validateOwnership(
        Order order,
        User user
    ) {

        if (!java.util.Objects.equals(
            order.getUser().getId(),
            user.getId()
        )) {

            throw new PaymentNotAllowedException(
                "You cannot pay for another user's order."
            );
        }
    }

    private void validateOrderStatus(
        Order order
    ) {

        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            throw new PaymentNotAllowedException(
                "Payment can only be created for orders in PENDING_PAYMENT status."
            );
        }
    }

    private void validateDuplicatePayment(
        Long orderId
    ) {

        if (paymentRepository.findByOrderId(orderId).isPresent()) {

            throw new PaymentNotAllowedException(
                "Payment already exists for this order."
            );
        }
    }
}
