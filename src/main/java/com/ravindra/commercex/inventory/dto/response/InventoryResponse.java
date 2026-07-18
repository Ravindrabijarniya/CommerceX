package com.ravindra.commercex.inventory.dto.response;

import com.ravindra.commercex.inventory.entity.InventoryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponse {

    private Long id;

    private Long productId;

    private String productName;

    private String sku;

    private Integer stockQuantity;

    private Integer reservedQuantity;

    private Integer availableQuantity;

    private Integer lowStockThreshold;

    private InventoryStatus stockStatus;
}
