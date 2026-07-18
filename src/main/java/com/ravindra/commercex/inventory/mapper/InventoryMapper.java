package com.ravindra.commercex.inventory.mapper;

import com.ravindra.commercex.inventory.dto.response.InventoryResponse;
import com.ravindra.commercex.inventory.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "sku", source = "product.sku")
    @Mapping(target = "availableQuantity", expression = "java(inventory.getAvailableQuantity())")
    InventoryResponse toResponse(Inventory inventory);
}
