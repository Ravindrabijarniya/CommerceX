package com.ravindra.commercex.inventory.validation;

import com.ravindra.commercex.cart.entity.Cart;
import com.ravindra.commercex.cart.entity.CartItem;
import com.ravindra.commercex.inventory.entity.Inventory;
import com.ravindra.commercex.inventory.exception.InventoryException;
import com.ravindra.commercex.inventory.exception.InventoryNotFoundException;
import com.ravindra.commercex.inventory.repository.InventoryRepository;
import com.ravindra.commercex.product.entity.Product;
import com.ravindra.commercex.product.exception.ProductNotFoundException;
import com.ravindra.commercex.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryBusinessValidator {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public Product validateProductExists(Long productId) {

        return productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public void validateInventoryDoesNotExist(Long productId) {

        if (inventoryRepository.existsByProductId(productId)) {
            throw new InventoryException(
                "Inventory already exists for product id: " + productId
            );
        }
    }

    public Inventory validateInventoryExists(Long productId) {

        return inventoryRepository.findByProductId(productId)
            .orElseThrow(() ->
                new InventoryException(
                    "Inventory not found for product id: " + productId
                ));
    }

    public void validateCartInventory(Cart cart) {

        for (CartItem cartItem : cart.getItems()) {

            Inventory inventory = inventoryRepository
                .findByProduct(cartItem.getProduct())
                .orElseThrow(() ->
                    new InventoryNotFoundException(
                        cartItem.getProduct().getId()
                    ));

            inventory.validateAvailableQuantity(
                cartItem.getQuantity()
            );
        }

    }
}
