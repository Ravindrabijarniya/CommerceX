package com.ravindra.commercex.cart.entity;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.cart.exception.CartItemNotFoundException;
import com.ravindra.commercex.cart.exception.InvalidCartOperationException;
import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "carts",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_cart_user",
            columnNames = "user_id"
        )
    }
)
public class Cart extends BaseEntity {



    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(name = "fk_cart_user")
    )
    private User user;

    @Builder.Default
    @OneToMany(
        mappedBy = "cart",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<CartItem> items = new LinkedHashSet<>();


    public static Cart create(User user) {
        Objects.requireNonNull(user, "User must not be null.");

        return Cart.builder()
            .user(user)
            .build();
    }



    public void addProduct(Product product, int quantity) {

        validateProduct(product);
        validateQuantity(quantity);

        Optional<CartItem> existingItem = findItem(product.getId());

        if (existingItem.isPresent()) {
            existingItem.get().increaseQuantity(quantity);
            return;
        }

        CartItem cartItem = CartItem.create(
            this,
            product,
            quantity
        );

        items.add(cartItem);
    }

    public void updateQuantity(Long productId, int quantity) {

        validateProductId(productId);

        CartItem item = findItem(productId)
            .orElseThrow(() ->
                new CartItemNotFoundException(productId
                ));

        if (quantity == 0) {
            removeProduct(productId);
            return;
        }

        item.changeQuantity(quantity);
    }

    public void removeProduct(Long productId) {

        validateProductId(productId);

        CartItem item = findItem(productId)
            .orElseThrow(() ->
                new CartItemNotFoundException(productId

                ));

        items.remove(item);
    }

    public void clear() {
        items.clear();
    }

    public BigDecimal calculateSubtotal() {

        return items.stream()
            .map(CartItem::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int calculateTotalItems() {

        return items.stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }



    private Optional<CartItem> findItem(Long productId) {

        return items.stream()
            .filter(item ->
                item.getProduct().getId().equals(productId))
            .findFirst();
    }



    private void validateProduct(Product product) {

        Objects.requireNonNull(product, "Product must not be null.");
    }

    private void validateProductId(Long productId) {

        Objects.requireNonNull(productId, "Product id must not be null.");
    }

    private void validateQuantity(int quantity) {

        if (quantity <= 0) {
            throw new InvalidCartOperationException(
                "Quantity must be greater than zero."
            );
        }
    }



    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Cart cart)) return false;
        return getId() != null && getId().equals(cart.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
