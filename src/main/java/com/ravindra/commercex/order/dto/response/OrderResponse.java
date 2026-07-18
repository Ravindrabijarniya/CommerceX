package com.ravindra.commercex.order.dto.response;

import com.ravindra.commercex.order.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

    Long id,

    String orderNumber,

    OrderStatus status,

    ShippingAddressResponse shippingAddress,

    OrderPricingResponse pricing,

    List<OrderItemResponse> items,

    LocalDateTime createdAt,

    LocalDateTime updatedAt

) {
}
