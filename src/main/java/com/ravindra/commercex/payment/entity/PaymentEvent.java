package com.ravindra.commercex.payment.entity;


import com.ravindra.commercex.common.entity.BaseEntity;
import com.ravindra.commercex.payment.enums.PaymentEventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
    name = "payment_events",
    indexes = {

        @Index(
            name = "idx_payment_event_payment",
            columnList = "payment_id"
        ),

        @Index(
            name = "idx_payment_event_type",
            columnList = "event_type"
        )
    }
)
public class PaymentEvent extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "payment_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_payment_event_payment"
        )
    )
    private Payment payment;



    @Enumerated(EnumType.STRING)
    @Column(
        name = "event_type",
        nullable = false,
        length = 50
    )
    private PaymentEventType eventType;



    @Column(length = 500)
    private String message;



    @Column(columnDefinition = "TEXT")
    private String metadata;



    @Column(nullable = false)
    private LocalDateTime occurredAt;



    public static PaymentEvent create(
        PaymentEventType eventType,
        String message,
        String metadata
    ){

        Objects.requireNonNull(eventType);


        return PaymentEvent.builder()
            .eventType(eventType)
            .message(message)
            .metadata(metadata)
            .occurredAt(
                LocalDateTime.now()
            )
            .build();
    }



    public void assignPayment(
        Payment payment
    ){

        Objects.requireNonNull(payment);

        this.payment = payment;
    }



    @Override
    public boolean equals(Object o){

        if(this == o)
            return true;


        if(!(o instanceof PaymentEvent other))
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
