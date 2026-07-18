package com.ravindra.commercex.cart.exception;

import com.ravindra.commercex.common.exception.ResourceNotFoundException;

public class CartNotFoundException extends ResourceNotFoundException {

    public CartNotFoundException(Long userId) {
        super("Cart not found for user id: " + userId);
    }

}
