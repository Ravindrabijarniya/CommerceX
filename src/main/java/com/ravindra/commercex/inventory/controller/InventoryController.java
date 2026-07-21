package com.ravindra.commercex.inventory.controller;

import com.ravindra.commercex.inventory.dto.request.AddStockRequest;
import com.ravindra.commercex.inventory.dto.request.CreateInventoryRequest;
import com.ravindra.commercex.inventory.dto.request.DeductReservedStockRequest;
import com.ravindra.commercex.inventory.dto.request.ReleaseStockRequest;
import com.ravindra.commercex.inventory.dto.request.ReserveStockRequest;
import com.ravindra.commercex.inventory.dto.request.UpdateLowStockThresholdRequest;
import com.ravindra.commercex.inventory.dto.response.InventoryResponse;
import com.ravindra.commercex.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/products/{productId}/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(
        @PathVariable Long productId,
        @Valid @RequestBody CreateInventoryRequest request
    ) {
        return inventoryService.createInventory(productId, request);
    }

    @GetMapping("/products/{productId}/inventory")
    public InventoryResponse getInventory(
        @PathVariable Long productId
    ) {
        return inventoryService.getInventory(productId);
    }

    @PutMapping("/products/{productId}/inventory/stock")
    public InventoryResponse addStock(
        @PathVariable Long productId,
        @Valid @RequestBody AddStockRequest request
    ) {
        return inventoryService.addStock(productId, request);
    }

    @PutMapping("/products/{productId}/inventory/reserve")
    public InventoryResponse reserveStock(
        @PathVariable Long productId,
        @Valid @RequestBody ReserveStockRequest request
    ) {
        return inventoryService.reserveStock(productId, request);
    }

    @PutMapping("/products/{productId}/inventory/release")
    public InventoryResponse releaseStock(
        @PathVariable Long productId,
        @Valid @RequestBody ReleaseStockRequest request
    ) {
        return inventoryService.releaseStock(productId, request);
    }

    @PutMapping("/products/{productId}/inventory/deduct")
    public InventoryResponse deductReservedStock(
        @PathVariable Long productId,
        @Valid @RequestBody DeductReservedStockRequest request
    ) {
        return inventoryService.deductReservedStock(productId, request);
    }

    @PutMapping("/products/{productId}/inventory/low-stock-threshold")
    public InventoryResponse updateLowStockThreshold(
        @PathVariable Long productId,
        @Valid @RequestBody UpdateLowStockThresholdRequest request
    ) {
        return inventoryService.updateLowStockThreshold(productId, request);
    }

    @GetMapping("/inventories/low-stock")
    public List<InventoryResponse> getLowStockInventories() {
        return inventoryService.getLowStockInventories();
    }
}
