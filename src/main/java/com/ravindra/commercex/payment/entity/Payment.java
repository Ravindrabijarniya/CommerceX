package com.ravindra.commercex.payment.entity;


import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.order.entity.Order;
import com.ravindra.commercex.payment.enums.PaymentMethod;
import com.ravindra.commercex.payment.enums.PaymentProviderType;
import com.ravindra.commercex.payment.enums.PaymentStatus;
import com.ravindra.commercex.payment.exception.InvalidPaymentStateException;
import com.ravindra.commercex.payment.exception.PaymentAlreadyCompletedException;

import java.util.Objects;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "payments",
    indexes = {

        @Index(
            name = "idx_payment_order",
            columnList = "order_id"
        ),

        @Index(
            name = "idx_payment_status",
            columnList = "status"
        ),

        @Index(
            name = "idx_payment_idempotency",
            columnList = "idempotency_key"
        )
    }
)
public class Payment extends BaseEntity {


    @Column(
        nullable = false,
        unique = true,
        length = 50
    )
    private String paymentNumber;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "order_id",
        nullable = false,
        unique = true,
        foreignKey = @ForeignKey(
            name = "fk_payment_order"
        )
    )
    private Order order;


    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;


    @Column(nullable = false,length = 10)
    private String currency;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private PaymentProviderType provider;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private PaymentMethod paymentMethod;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private PaymentStatus status;


    @Column(length = 100)
    private String providerReference;


    @Column(
        name = "idempotency_key",
        nullable = false,
        unique = true,
        length = 100
    )
    private String idempotencyKey;


    private LocalDateTime completedAt;


    private LocalDateTime failedAt;



    @Builder.Default
    @OneToMany(
        mappedBy = "payment",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<PaymentTransaction> transactions =
        new LinkedHashSet<>();



    @Builder.Default
    @OneToMany(
        mappedBy = "payment",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<PaymentEvent> events =
        new LinkedHashSet<>();


    // domain methods

    public static Payment create(
        String paymentNumber,
        Order order,
        BigDecimal amount,
        String currency,
        PaymentProviderType provider,
        PaymentMethod paymentMethod,
        String idempotencyKey
    ) {

        Objects.requireNonNull(paymentNumber);
        Objects.requireNonNull(order);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
        Objects.requireNonNull(provider);
        Objects.requireNonNull(paymentMethod);
        Objects.requireNonNull(idempotencyKey);


        return Payment.builder()
            .paymentNumber(paymentNumber)
            .order(order)
            .amount(amount)
            .currency(currency)
            .provider(provider)
            .paymentMethod(paymentMethod)
            .idempotencyKey(idempotencyKey)
            .status(PaymentStatus.CREATED)
            .build();
    }

    public void markInitiated() {

        ensureStatus(PaymentStatus.CREATED);

        this.status = PaymentStatus.INITIATED;
    }


    public void markPending() {

        ensureStatus(
            PaymentStatus.INITIATED
        );

        this.status = PaymentStatus.PENDING;
    }


    public void markSuccess(String providerReference) {

        Objects.requireNonNull(providerReference);

        if (status == PaymentStatus.SUCCESS) {
            throw new PaymentAlreadyCompletedException();
        }

        ensureStatus(PaymentStatus.INITIATED);

        this.status = PaymentStatus.SUCCESS;
        this.providerReference = providerReference;
        this.completedAt = LocalDateTime.now();
    }


    public void markFailed() {


        if(status == PaymentStatus.SUCCESS){

            throw new PaymentAlreadyCompletedException();
        }


        this.status = PaymentStatus.FAILED;

        this.failedAt =
            LocalDateTime.now();
    }


    public void cancel(){

        if(
            status == PaymentStatus.SUCCESS
                ||
                status == PaymentStatus.REFUNDED
        ){

            throw new InvalidPaymentStateException(
                status,
                PaymentStatus.CANCELLED
            );
        }


        this.status =
            PaymentStatus.CANCELLED;
    }



    public void addTransaction(
        PaymentTransaction transaction
    ){

        Objects.requireNonNull(transaction);

        transaction.assignPayment(this);

        transactions.add(transaction);

    }

    public void addEvent(
        PaymentEvent event
    ){

        Objects.requireNonNull(event);

        event.assignPayment(this);

        events.add(event);
    }


    private void ensureStatus(
        PaymentStatus expected
    ){

        if(this.status != expected){

            throw new InvalidPaymentStateException(
                this.status,
                expected
            );
        }
    }


    @Override
    public boolean equals(Object o) {

        if(this == o)
            return true;


        if(!(o instanceof Payment other))
            return false;


        return getId()!=null &&
            Objects.equals(
                getId(),
                other.getId()
            );
    }


    @Override
    public int hashCode(){

        return Objects.hash(getId());
    }


}
