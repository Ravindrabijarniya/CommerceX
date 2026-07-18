package com.ravindra.commercex.cart.validation;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.cart.entity.Cart;
import com.ravindra.commercex.cart.entity.CartItem;
import com.ravindra.commercex.cart.exception.CartItemNotFoundException;
import com.ravindra.commercex.cart.repository.CartRepository;
import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.product.exception.ProductNotFoundException;
import com.ravindra.commercex.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartBusinessValidator {


    private final CartRepository cartRepository;

    private final ProductRepository productRepository;



    public Product validateProductExists(Long productId) {

        return productRepository.findById(productId)
            .orElseThrow(() ->
                new ProductNotFoundException(productId)
            );
    }



    public Cart getOrCreateCart(User user) {

        return cartRepository.findByUser(user)
            .orElseGet(() ->
                Cart.create(user)
            );
    }



    public void validateCartItemExists(
        Cart cart,
        Long productId
    ) {

        boolean exists = cart.getItems()
            .stream()
            .anyMatch(item ->
                item.getProduct()
                    .getId()
                    .equals(productId)
            );



        if (!exists) {
            throw new CartItemNotFoundException(productId);
        }
    }

}
