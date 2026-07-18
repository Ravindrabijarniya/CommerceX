package com.ravindra.commercex.order.entity;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.order.exception.InvalidOrderStateException;
import com.ravindra.commercex.order.exception.OrderAlreadyCancelledException;
import com.ravindra.commercex.order.exception.OrderCancellationNotAllowedException;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "orders",
    indexes = {
        @Index(name = "idx_orders_user", columnList = "user_id"),
        @Index(name = "idx_orders_status", columnList = "status"),
        @Index(name = "idx_orders_created_at", columnList = "created_at")
    }
)
public class Order extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_orders_user")
    )
    private User user;

    @Column(nullable = false)
    private Integer totalItems;

    @Embedded
    private ShippingAddress shippingAddress;

    @Embedded
    private OrderPricing pricing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status;

    @Builder.Default
    @OneToMany(
        mappedBy = "order",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<OrderItem> items = new LinkedHashSet<>();

    public static Order create(
        String orderNumber,
        User user,
        ShippingAddress shippingAddress,
        OrderPricing pricing,
        Integer totalItems
    ) {

        Objects.requireNonNull(orderNumber);
        Objects.requireNonNull(user);
        Objects.requireNonNull(shippingAddress);
        Objects.requireNonNull(pricing);
        Objects.requireNonNull(totalItems);

        return Order.builder()
            .orderNumber(orderNumber)
            .user(user)
            .totalItems(totalItems)
            .shippingAddress(shippingAddress)
            .pricing(pricing)
            .status(OrderStatus.PENDING_PAYMENT)
            .build();
    }

    public void addItem(OrderItem item) {

        Objects.requireNonNull(item);

        item.assignOrder(this);
        items.add(item);
    }

    public void addItems(Iterable<OrderItem> orderItems) {
        orderItems.forEach(this::addItem);
    }

    public void markAsPaid() {

        ensureStatus(OrderStatus.PENDING_PAYMENT);

        this.status = OrderStatus.PAID;
    }

    public void startProcessing() {

        ensureStatus(OrderStatus.PAID);

        this.status = OrderStatus.PROCESSING;
    }

    public void markAsShipped() {

        ensureStatus(OrderStatus.PROCESSING);

        this.status = OrderStatus.SHIPPED;
    }

    public void markAsDelivered() {

        ensureStatus(OrderStatus.SHIPPED);

        this.status = OrderStatus.DELIVERED;
    }

    public void cancel() {

        if (status == OrderStatus.CANCELLED) {
            throw new OrderAlreadyCancelledException();
        }

        if (status == OrderStatus.SHIPPED ||
            status == OrderStatus.DELIVERED) {

            throw new OrderCancellationNotAllowedException();
        }

        status = OrderStatus.CANCELLED;
    }

    private void ensureStatus(OrderStatus expected) {

        if (status != expected) {
            throw new InvalidOrderStateException(
                    expected , status
            );
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order other)) return false;
        return getId() != null &&
            Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
