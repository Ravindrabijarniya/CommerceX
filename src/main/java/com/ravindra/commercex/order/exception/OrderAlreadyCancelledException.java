package com.ravindra.commercex.order.exception;

import com.ravindra.commercex.common.exception.InvalidOperationException;

public class OrderAlreadyCancelledException extends InvalidOperationException {

    public OrderAlreadyCancelledException() {
        super("Order is already cancelled.");
    }

}
