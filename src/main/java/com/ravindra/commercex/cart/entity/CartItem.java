package com.ravindra.commercex.cart.entity;

import com.ravindra.commercex.cart.exception.InvalidCartOperationException;
import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "cart_items",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_cart_item",
            columnNames = {"cart_id", "product_id"}
        )
    }
)
public class CartItem extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "cart_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_cart_item_cart")
    )
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "product_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_cart_item_product")
    )
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(
        name = "unit_price",
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal unitPrice;



    public static CartItem create(
        Cart cart,
        Product product,
        int quantity
    ) {

//        if (product.getPrice() == null || product.getPrice().signum() < 0) {
//            throw new CartException("Invalid product price.");
//        }

        Objects.requireNonNull(cart, "Cart must not be null.");
        Objects.requireNonNull(product, "Product must not be null.");

        validateQuantity(quantity);

        return CartItem.builder()
            .cart(cart)
            .product(product)
            .quantity(quantity)
            .unitPrice(product.getPrice())
            .build();
    }


    public void increaseQuantity(int quantity) {

        validateQuantity(quantity);

        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity) {

        validateQuantity(quantity);

        if (quantity >= this.quantity) {
            throw new InvalidCartOperationException(
                "Quantity to decrease must be less than current quantity."
            );
        }

        this.quantity -= quantity;
    }

    public void changeQuantity(int quantity) {

        validateQuantity(quantity);

        this.quantity = quantity;
    }

    public BigDecimal getLineTotal() {

        return unitPrice.multiply(
            BigDecimal.valueOf(quantity)
        );
    }



    private static void validateQuantity(int quantity) {

        if (quantity <= 0) {
            throw new InvalidCartOperationException(
                "Quantity must be greater than zero."
            );
        }
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof CartItem other)) {
            return false;
        }

        return getId() != null &&
            getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
