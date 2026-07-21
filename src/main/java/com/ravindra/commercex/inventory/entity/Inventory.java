package com.ravindra.commercex.inventory.entity;

import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.inventory.exception.InsufficientInventoryException;
import com.ravindra.commercex.inventory.exception.InventoryException;
import com.ravindra.commercex.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "inventories",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_inventory_product",
            columnNames = "product_id"
        )
    }
)
public class Inventory extends BaseEntity {

    private static final Integer DEFAULT_LOW_STOCK_THRESHOLD = 5;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(name = "fk_inventory_product")
    )
    private Product product;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity;

    @Column(name = "low_stock_threshold", nullable = false)
    private Integer lowStockThreshold;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_status", nullable = false, length = 30)
    private InventoryStatus stockStatus;



    public static Inventory create(Product product) {

        Objects.requireNonNull(product, "Product cannot be null");

        return Inventory.builder()
            .product(product)
            .stockQuantity(0)
            .reservedQuantity(0)
            .lowStockThreshold(DEFAULT_LOW_STOCK_THRESHOLD)
            .stockStatus(InventoryStatus.OUT_OF_STOCK)
            .build();
    }


    public void addStock(Integer quantity) {

        validatePositive(quantity);

        stockQuantity += quantity;

        updateStockStatus();
    }


    public void reserve(Integer quantity) {

        validatePositive(quantity);

        if (quantity > getAvailableQuantity()) {
            throw new InventoryException("Insufficient available stock.");
        }

        reservedQuantity += quantity;

        updateStockStatus();
    }


    public void release(Integer quantity) {

        validatePositive(quantity);

        if (quantity > reservedQuantity) {
            throw new InventoryException(
                "Cannot release more than reserved quantity."
            );
        }

        reservedQuantity -= quantity;

        updateStockStatus();
    }


    public void deductReserved(Integer quantity) {

        validatePositive(quantity);

        if (quantity > reservedQuantity) {
            throw new InventoryException(
                "Cannot deduct more than reserved quantity."
            );
        }

        reservedQuantity -= quantity;
        stockQuantity -= quantity;

        updateStockStatus();
    }


    public void changeLowStockThreshold(Integer threshold) {

        validatePositiveOrZero(threshold);

        this.lowStockThreshold = threshold;

        updateStockStatus();
    }


    public Integer getAvailableQuantity() {

        return stockQuantity - reservedQuantity;
    }


    private void updateStockStatus() {

        int available = getAvailableQuantity();

        if (available <= 0) {
            stockStatus = InventoryStatus.OUT_OF_STOCK;
        } else if (available <= lowStockThreshold) {
            stockStatus = InventoryStatus.LOW_STOCK;
        } else {
            stockStatus = InventoryStatus.IN_STOCK;
        }
    }

    private void validatePositive(Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new InventoryException(
                "Quantity must be greater than zero."
            );
        }
    }

    private void validatePositiveOrZero(Integer quantity) {

        if (quantity == null || quantity < 0) {
            throw new InventoryException(
                "Quantity cannot be negative."
            );
        }
    }

    public void validateAvailableQuantity(Integer requestedQuantity) {

        if (stockQuantity < requestedQuantity) {
            throw new InsufficientInventoryException();
        }
    }

    public void removeStock(Integer quantity){

        validatePositive(quantity);


        if(quantity > getAvailableQuantity()){

            throw new InventoryException(
                "Cannot remove more than available stock"
            );

        }


        stockQuantity -= quantity;

        updateStockStatus();
    }
}
