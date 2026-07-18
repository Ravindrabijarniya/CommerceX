package com.ravindra.commercex.order.dto.response;

public record ShippingAddressResponse(

    String fullName,

    String phoneNumber,

    String addressLine1,

    String addressLine2,

    String city,

    String state,

    String postalCode,

    String country

) {
}
