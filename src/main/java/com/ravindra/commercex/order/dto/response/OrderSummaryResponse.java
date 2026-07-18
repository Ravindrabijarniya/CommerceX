package com.ravindra.commercex.order.dto.response;

import com.ravindra.commercex.order.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSummaryResponse(

    Long id,

    String orderNumber,

    OrderStatus status,

    BigDecimal grandTotal,

    Integer totalItems,

    LocalDateTime createdAt

) {
}
