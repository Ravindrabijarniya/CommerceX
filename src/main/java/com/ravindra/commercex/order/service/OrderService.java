package com.ravindra.commercex.order.service;

import com.ravindra.commercex.order.dto.request.CreateOrderRequest;
import com.ravindra.commercex.order.dto.response.OrderResponse;
import com.ravindra.commercex.order.dto.response.OrderSummaryResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderSummaryResponse> getOrders();

    OrderResponse getOrder(Long id);

    void cancelOrder(Long id);

}
