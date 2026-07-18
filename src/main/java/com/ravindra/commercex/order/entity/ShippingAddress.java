package com.ravindra.commercex.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class ShippingAddress {

    @Column(name = "shipping_full_name", nullable = false)
    private String fullName;

    @Column(name = "shipping_phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "shipping_address_line1", nullable = false)
    private String addressLine1;

    @Column(name = "shipping_address_line2")
    private String addressLine2;

    @Column(name = "shipping_city", nullable = false)
    private String city;

    @Column(name = "shipping_state", nullable = false)
    private String state;

    @Column(name = "shipping_postal_code", nullable = false)
    private String postalCode;

    @Column(name = "shipping_country", nullable =false)
    private String country;

    public static ShippingAddress of(
        String fullName,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country
    ) {
        return ShippingAddress.builder()
            .fullName(fullName)
            .phoneNumber(phoneNumber)
            .addressLine1(addressLine1)
            .addressLine2(addressLine2)
            .city(city)
            .state(state)
            .postalCode(postalCode)
            .country(country)
            .build();
    }
}
