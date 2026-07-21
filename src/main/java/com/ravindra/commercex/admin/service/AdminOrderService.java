package com.ravindra.commercex.admin.service;


import com.ravindra.commercex.order.dto.response.OrderResponse;
import com.ravindra.commercex.order.dto.response.OrderSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminOrderService {


    Page<OrderSummaryResponse> getOrders(
        Pageable pageable
    );


    OrderResponse getOrder(
        Long id
    );


    void markProcessing(
        Long id
    );


    void markShipped(
        Long id
    );


    void markDelivered(
        Long id
    );


    void cancelOrder(
        Long id
    );

}
