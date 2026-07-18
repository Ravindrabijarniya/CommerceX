package com.ravindra.commercex.order.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(

    Long id,

    Long productId,

    String productName,

    String productSlug,

    BigDecimal unitPrice,

    Integer quantity,

    BigDecimal subtotal

) {
}
