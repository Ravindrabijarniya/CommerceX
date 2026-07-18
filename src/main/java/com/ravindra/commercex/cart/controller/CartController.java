package com.ravindra.commercex.cart.controller;

import com.ravindra.commercex.cart.dto.request.AddCartItemRequest;
import com.ravindra.commercex.cart.dto.request.UpdateCartItemRequest;
import com.ravindra.commercex.cart.dto.response.CartResponse;
import com.ravindra.commercex.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;


    @GetMapping
    public CartResponse getCart() {
        return cartService.getCart();
    }


    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addItem(
        @Valid @RequestBody AddCartItemRequest request
    ) {
        return cartService.addItem(request);
    }



    @PutMapping("/items/{productId}")
    public CartResponse updateItem(
        @PathVariable Long productId,
        @Valid @RequestBody UpdateCartItemRequest request
    ) {
        return cartService.updateItem(productId, request);
    }


    @DeleteMapping("/items/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(
        @PathVariable Long productId
    ) {
        cartService.removeItem(productId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart() {
        cartService.clearCart();
    }

}
