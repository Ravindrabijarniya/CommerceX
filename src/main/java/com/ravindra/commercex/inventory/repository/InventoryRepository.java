package com.ravindra.commercex.inventory.repository;

import com.ravindra.commercex.inventory.entity.Inventory;
import com.ravindra.commercex.inventory.entity.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    boolean existsByProductId(Long productId);

    List<Inventory> findByStockStatus(InventoryStatus stockStatus);

    List<Inventory> findByStockStatusIn(List<InventoryStatus> statuses);
}
