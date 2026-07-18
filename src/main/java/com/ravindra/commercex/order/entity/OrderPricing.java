package com.ravindra.commercex.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class OrderPricing {

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal shippingCharge;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal tax;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal grandTotal;

    public static OrderPricing of(
        BigDecimal subtotal,
        BigDecimal shippingCharge,
        BigDecimal discount,
        BigDecimal tax,
        BigDecimal grandTotal
    ) {
        return OrderPricing.builder()
            .subtotal(subtotal)
            .shippingCharge(shippingCharge)
            .discount(discount)
            .tax(tax)
            .grandTotal(grandTotal)
            .build();
    }
}
