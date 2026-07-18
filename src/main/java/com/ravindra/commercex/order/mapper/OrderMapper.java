package com.ravindra.commercex.order.mapper;

import com.ravindra.commercex.order.dto.response.OrderItemResponse;
import com.ravindra.commercex.order.dto.response.OrderPricingResponse;
import com.ravindra.commercex.order.dto.response.OrderResponse;
import com.ravindra.commercex.order.dto.response.OrderSummaryResponse;
import com.ravindra.commercex.order.dto.response.ShippingAddressResponse;
import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.order.entity.OrderItem;
import com.ravindra.commercex.order.entity.OrderPricing;
import com.ravindra.commercex.order.entity.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    @Mapping(target = "grandTotal", source = "pricing.grandTotal")
    @Mapping(target = "totalItems", expression = "java(order.getItems().size())")
    OrderSummaryResponse toSummaryResponse(Order order);

    ShippingAddressResponse toResponse(ShippingAddress address);

    OrderPricingResponse toResponse(OrderPricing pricing);

    OrderItemResponse toResponse(OrderItem item);

}
