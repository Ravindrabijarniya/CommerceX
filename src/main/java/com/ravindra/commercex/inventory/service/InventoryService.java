package com.ravindra.commercex.inventory.service;

import com.ravindra.commercex.inventory.dto.request.*;
import com.ravindra.commercex.inventory.dto.response.InventoryResponse;

import java.util.List;
//
//import java.awt.*;

public interface InventoryService {

    InventoryResponse createInventory(
        Long productId,
        CreateInventoryRequest request
    );

    InventoryResponse getInventory(Long productId);

    InventoryResponse addStock(
        Long productId,
        AddStockRequest request
    );

    InventoryResponse reserveStock(
        Long productId,
        ReserveStockRequest request
    );

    InventoryResponse releaseStock(
        Long productId,
        ReleaseStockRequest request
    );

    InventoryResponse deductReservedStock(
        Long productId,
        DeductReservedStockRequest request
    );

    InventoryResponse updateLowStockThreshold(
        Long productId,
        UpdateLowStockThresholdRequest request
    );

    List<InventoryResponse> getLowStockInventories();
}
