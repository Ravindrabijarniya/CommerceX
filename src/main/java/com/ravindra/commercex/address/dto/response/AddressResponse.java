package com.ravindra.commercex.address.dto.response;

import com.ravindra.commercex.address.entity.AddressType;

import java.time.LocalDateTime;

public record AddressResponse(

    Long id,

    String fullName,

    String phoneNumber,

    String addressLine1,

    String addressLine2,

    String city,

    String state,

    String postalCode,

    String country,

    AddressType addressType,

    boolean defaultAddress,

    LocalDateTime createdAt,

    LocalDateTime updatedAt

) {
}
