package com.ravindra.commercex.cart.mapper;

import com.ravindra.commercex.cart.dto.response.CartItemResponse;
import com.ravindra.commercex.cart.dto.response.CartResponse;
import com.ravindra.commercex.cart.entity.Cart;
import com.ravindra.commercex.cart.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "sku", source = "product.sku")
    @Mapping(target = "lineTotal", expression = "java(cartItem.getLineTotal())")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    @Mapping(target = "items", source = "items")
    @Mapping(target = "totalItems", expression = "java(cart.calculateTotalItems())")
    @Mapping(target = "subtotal", expression = "java(cart.calculateSubtotal())")
    CartResponse toCartResponse(Cart cart);

}
