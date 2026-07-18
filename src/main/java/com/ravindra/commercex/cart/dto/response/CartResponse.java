package com.ravindra.commercex.cart.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Builder
public class CartResponse {

    private Long id;

    private Set<CartItemResponse> items;

    private int totalItems;

    private BigDecimal subtotal;

}
