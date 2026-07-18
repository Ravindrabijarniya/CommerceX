package com.ravindra.commercex.cart.service;

import com.ravindra.commercex.cart.dto.request.AddCartItemRequest;
import com.ravindra.commercex.cart.dto.request.UpdateCartItemRequest;
import com.ravindra.commercex.cart.dto.response.CartResponse;

public interface CartService {


    CartResponse getCart();


    CartResponse addItem(
        AddCartItemRequest request
    );


    CartResponse updateItem(
        Long productId,
        UpdateCartItemRequest request
    );


    void removeItem(
        Long productId
    );


    void clearCart();

}
