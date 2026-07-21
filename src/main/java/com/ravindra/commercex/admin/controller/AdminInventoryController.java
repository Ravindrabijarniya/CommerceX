package com.ravindra.commercex.admin.controller;


import com.ravindra.commercex.inventory.dto.request.*;
import com.ravindra.commercex.inventory.dto.response.InventoryResponse;
import com.ravindra.commercex.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin/inventory")
@RequiredArgsConstructor
public class AdminInventoryController {


    private final InventoryService inventoryService;



    @PostMapping("/{productId}")
    public ResponseEntity<InventoryResponse> createInventory(
        @PathVariable Long productId,
        @Valid @RequestBody CreateInventoryRequest request
    ){

        return ResponseEntity.ok(
            inventoryService.createInventory(
                productId,
                request
            )
        );

    }



    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(
        @PathVariable Long productId
    ){

        return ResponseEntity.ok(
            inventoryService.getInventory(productId)
        );

    }




    @PostMapping("/{productId}/increase")
    public ResponseEntity<InventoryResponse> addStock(
        @PathVariable Long productId,
        @Valid @RequestBody AddStockRequest request
    ){

        return ResponseEntity.ok(
            inventoryService.addStock(
                productId,
                request
            )
        );

    }




    @PostMapping("/{productId}/decrease")
    public ResponseEntity<InventoryResponse> deductStock(
        @PathVariable Long productId,
        @Valid @RequestBody DeductReservedStockRequest request
    ){

        return ResponseEntity.ok(
            inventoryService.deductReservedStock(
                productId,
                request
            )
        );

    }





    @PatchMapping("/{productId}/threshold")
    public ResponseEntity<InventoryResponse> updateThreshold(
        @PathVariable Long productId,
        @Valid @RequestBody UpdateLowStockThresholdRequest request
    ){

        return ResponseEntity.ok(
            inventoryService.updateLowStockThreshold(
                productId,
                request
            )
        );

    }





    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponse>> getLowStock(){

        return ResponseEntity.ok(
            inventoryService.getLowStockInventories()
        );

    }

}
