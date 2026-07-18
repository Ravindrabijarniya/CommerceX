package com.ravindra.commercex.order.exception;

import com.ravindra.commercex.common.exception.InvalidOperationException;

public class OrderCancellationNotAllowedException extends InvalidOperationException {

    public OrderCancellationNotAllowedException() {
        super("Order cannot be cancelled after it has been shipped.");
    }

}
