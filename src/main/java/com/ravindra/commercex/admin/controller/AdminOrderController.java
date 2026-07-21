package com.ravindra.commercex.admin.controller;


import com.ravindra.commercex.admin.service.AdminOrderService;
import com.ravindra.commercex.order.dto.response.OrderResponse;
import com.ravindra.commercex.order.dto.response.OrderSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {


    private final AdminOrderService adminOrderService;



    @GetMapping
    public ResponseEntity<Page<OrderSummaryResponse>> getOrders(
        Pageable pageable
    ){

        return ResponseEntity.ok(
            adminOrderService.getOrders(pageable)
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(
        @PathVariable Long id
    ){

        return ResponseEntity.ok(
            adminOrderService.getOrder(id)
        );

    }




    @PatchMapping("/{id}/processing")
    public ResponseEntity<Void> processing(
        @PathVariable Long id
    ){

        adminOrderService.markProcessing(id);

        return ResponseEntity.noContent().build();

    }




    @PatchMapping("/{id}/shipped")
    public ResponseEntity<Void> shipped(
        @PathVariable Long id
    ){

        adminOrderService.markShipped(id);

        return ResponseEntity.noContent().build();

    }




    @PatchMapping("/{id}/delivered")
    public ResponseEntity<Void> delivered(
        @PathVariable Long id
    ){

        adminOrderService.markDelivered(id);

        return ResponseEntity.noContent().build();

    }





    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(
        @PathVariable Long id
    ){

        adminOrderService.cancelOrder(id);

        return ResponseEntity.noContent().build();

    }

}
