package com.ravindra.commercex.order.validator;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.order.exception.OrderNotFoundException;
import com.ravindra.commercex.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderBusinessValidator {

    private final OrderRepository orderRepository;

    public Order getOrder(Long id, User user) {

        return orderRepository.findByIdAndUser(id, user)
            .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order getOrder(String orderNumber) {

        return orderRepository.findByOrderNumber(orderNumber)
            .orElseThrow(() ->
                new OrderNotFoundException(orderNumber));
    }

    public List<Order> getOrders(User user) {

        return orderRepository.findAllByUserOrderByCreatedAtDesc(user);
    }

}
