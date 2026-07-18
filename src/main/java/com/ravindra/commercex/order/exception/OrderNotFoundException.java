package com.ravindra.commercex.order.exception;

import com.ravindra.commercex.common.exception.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
    }

    public OrderNotFoundException(String orderNumber) {
        super("Order not found with order number: " + orderNumber);
    }

}
