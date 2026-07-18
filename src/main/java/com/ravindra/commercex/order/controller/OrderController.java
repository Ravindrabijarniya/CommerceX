package com.ravindra.commercex.order.controller;

import com.ravindra.commercex.order.dto.request.CreateOrderRequest;
import com.ravindra.commercex.order.dto.response.OrderResponse;
import com.ravindra.commercex.order.dto.response.OrderSummaryResponse;
import com.ravindra.commercex.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
        @Valid @RequestBody CreateOrderRequest request
    ) {

        OrderResponse response = orderService.createOrder(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderSummaryResponse>> getOrders() {

        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(
        @PathVariable Long id
    ) {

        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(
        @PathVariable Long id
    ) {

        orderService.cancelOrder(id);

        return ResponseEntity.noContent().build();
    }

}
