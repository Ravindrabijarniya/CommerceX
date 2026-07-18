package com.ravindra.commercex.cart.exception;

import com.ravindra.commercex.common.exception.ResourceNotFoundException;

public class CartItemNotFoundException extends ResourceNotFoundException {

    public CartItemNotFoundException(Long productId) {
        super("Cart item not found for product id: " + productId);
    }

}
