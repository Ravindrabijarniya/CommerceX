package com.ravindra.commercex.cart.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CartItemResponse {

    private Long productId;

    private String productName;

    private String sku;

    private BigDecimal unitPrice;

    private int quantity;

    private BigDecimal lineTotal;

}
