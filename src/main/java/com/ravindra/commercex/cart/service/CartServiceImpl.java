package com.ravindra.commercex.cart.service;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.cart.dto.request.AddCartItemRequest;
import com.ravindra.commercex.cart.dto.request.UpdateCartItemRequest;
import com.ravindra.commercex.cart.dto.response.CartResponse;
import com.ravindra.commercex.cart.entity.Cart;
import com.ravindra.commercex.cart.mapper.CartMapper;
import com.ravindra.commercex.cart.repository.CartRepository;
import com.ravindra.commercex.cart.validation.CartBusinessValidator;
import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.security.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;

    private final CartBusinessValidator validator;

    private final CartMapper mapper;

    private final CurrentUserService currentUserService;



    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart() {

        User user = currentUserService.getCurrentUser();

        Cart cart = validator.getOrCreateCart(user);

        return mapper.toCartResponse(cart);
    }


    @Override
    public CartResponse addItem(
        AddCartItemRequest request
    ) {

        User user = currentUserService.getCurrentUser();


        Product product =
            validator.validateProductExists(
                request.getProductId()
            );


        Cart cart =
            validator.getOrCreateCart(user);


        cart.addProduct(
            product,
            request.getQuantity()
        );


        Cart savedCart =
            cartRepository.save(cart);


        return mapper.toCartResponse(savedCart);
    }


    @Override
    public CartResponse updateItem(
        Long productId,
        UpdateCartItemRequest request
    ) {

        User user = currentUserService.getCurrentUser();


        Cart cart =
            validator.getOrCreateCart(user);


        validator.validateCartItemExists(
            cart,
            productId
        );


        cart.updateQuantity(
            productId,
            request.getQuantity()
        );


        return mapper.toCartResponse(
            cartRepository.save(cart)
        );
    }





    @Override
    public void removeItem(
        Long productId
    ) {

        User user = currentUserService.getCurrentUser();


        Cart cart =
            validator.getOrCreateCart(user);


        validator.validateCartItemExists(
            cart,
            productId
        );


        cart.removeProduct(productId);


        cartRepository.save(cart);
    }





    @Override
    public void clearCart() {


        User user =currentUserService.getCurrentUser();


        Cart cart =
            validator.getOrCreateCart(user);


        cart.clear();


        cartRepository.save(cart);
    }

}
