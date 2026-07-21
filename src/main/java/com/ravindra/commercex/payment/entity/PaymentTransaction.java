package com.ravindra.commercex.payment.entity;


import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.payment.enums.PaymentTransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "payment_transactions",
    indexes = {

        @Index(
            name = "idx_payment_transaction_payment",
            columnList = "payment_id"
        ),

        @Index(
            name = "idx_payment_transaction_provider",
            columnList = "provider_transaction_id"
        )
    }
)
public class PaymentTransaction extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "payment_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_payment_transaction_payment"
        )
    )
    private Payment payment;


    @Column(nullable = false)
    private Integer attemptNumber;


    @Column(length = 100)
    private String providerTransactionId;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private PaymentTransactionStatus status;


    @Column(length = 50)
    private String providerStatus;


    @Column(columnDefinition = "TEXT")
    private String requestPayload;


    @Column(columnDefinition = "TEXT")
    private String responsePayload;


    @Column(length = 500)
    private String failureReason;



    public static PaymentTransaction create(
        Integer attemptNumber
    ){

        Objects.requireNonNull(attemptNumber);


        return PaymentTransaction.builder()
            .attemptNumber(attemptNumber)
            .status(
                PaymentTransactionStatus.CREATED
            )
            .build();
    }



    public void assignPayment(
        Payment payment
    ){

        Objects.requireNonNull(payment);

        this.payment = payment;
    }



    public void markProcessing(){

        this.status =
            PaymentTransactionStatus.PROCESSING;
    }



    public void markSuccess(
        String providerTransactionId
    ){

        this.status =
            PaymentTransactionStatus.SUCCESS;

        this.providerTransactionId =
            providerTransactionId;
    }



    public void markFailed(
        String reason
    ){

        this.status =
            PaymentTransactionStatus.FAILED;

        this.failureReason =
            reason;
    }



    @Override
    public boolean equals(Object o){

        if(this == o)
            return true;


        if(!(o instanceof PaymentTransaction other))
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
