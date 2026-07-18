package com.ravindra.commercex.cart.exception;

import com.ravindra.commercex.common.exception.InvalidOperationException;

public class EmptyCartException extends InvalidOperationException {

    public EmptyCartException() {
        super("Cart is empty.");
    }

}
