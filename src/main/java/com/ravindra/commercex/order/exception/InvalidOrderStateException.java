package com.ravindra.commercex.order.exception;

import com.ravindra.commercex.common.exception.InvalidOperationException;
import com.ravindra.commercex.order.entity.OrderStatus;

public class InvalidOrderStateException extends InvalidOperationException {

    public InvalidOrderStateException(OrderStatus expected, OrderStatus actual) {
        super("Invalid order state. Expected: " + expected + ", but was: " + actual);
    }

}
