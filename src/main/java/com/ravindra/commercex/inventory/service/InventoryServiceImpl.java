package com.ravindra.commercex.inventory.service;

import com.ravindra.commercex.inventory.dto.request.*;
import com.ravindra.commercex.inventory.dto.response.InventoryResponse;
import com.ravindra.commercex.inventory.entity.Inventory;
import com.ravindra.commercex.inventory.entity.InventoryStatus;
import com.ravindra.commercex.inventory.mapper.InventoryMapper;
import com.ravindra.commercex.inventory.repository.InventoryRepository;
import com.ravindra.commercex.inventory.validation.InventoryBusinessValidator;
import com.ravindra.commercex.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryBusinessValidator validator;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse createInventory(
        Long productId,
        CreateInventoryRequest request) {

        Product product = validator.validateProductExists(productId);

        validator.validateInventoryDoesNotExist(productId);

        Inventory inventory = Inventory.create(product);

        if (request.getStockQuantity() > 0) {
            inventory.addStock(request.getStockQuantity());
        }

        if (request.getLowStockThreshold() != null) {
            inventory.changeLowStockThreshold(
                request.getLowStockThreshold()
            );
        }

        inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventory(Long productId) {

        Inventory inventory =
            validator.validateInventoryExists(productId);

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse addStock(
        Long productId,
        AddStockRequest request) {

        Inventory inventory =
            validator.validateInventoryExists(productId);

        inventory.addStock(request.getQuantity());

        inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse reserveStock(
        Long productId,
        ReserveStockRequest request) {

        Inventory inventory =
            validator.validateInventoryExists(productId);

        inventory.reserve(request.getQuantity());

        inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse releaseStock(
        Long productId,
        ReleaseStockRequest request) {

        Inventory inventory =
            validator.validateInventoryExists(productId);

        inventory.release(request.getQuantity());

        inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse deductReservedStock(
        Long productId,
        DeductReservedStockRequest request) {

        Inventory inventory =
            validator.validateInventoryExists(productId);

        inventory.deductReserved(request.getQuantity());

        inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    public InventoryResponse updateLowStockThreshold(
        Long productId,
        UpdateLowStockThresholdRequest request) {

        Inventory inventory =
            validator.validateInventoryExists(productId);

        inventory.changeLowStockThreshold(
            request.getLowStockThreshold()
        );

        inventoryRepository.save(inventory);

        return inventoryMapper.toResponse(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getLowStockInventories() {

        return inventoryRepository
            .findByStockStatusIn(
                List.of(
                    InventoryStatus.LOW_STOCK,
                    InventoryStatus.OUT_OF_STOCK
                )
            )
            .stream()
            .map(inventoryMapper::toResponse)
            .toList();
    }

}
