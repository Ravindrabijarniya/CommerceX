package com.ravindra.commercex.address.dto.request;

import com.ravindra.commercex.address.entity.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAddressRequest(

    @NotBlank(message = "Full name is required.")
    @Size(max = 100, message = "Full name must not exceed 100 characters.")
    String fullName,

    @NotBlank(message = "Phone number is required.")
    @Size(max = 20, message = "Phone number must not exceed 20 characters.")
    String phoneNumber,

    @NotBlank(message = "Address line 1 is required.")
    @Size(max = 255, message = "Address line 1 must not exceed 255 characters.")
    String addressLine1,

    @Size(max = 255, message = "Address line 2 must not exceed 255 characters.")
    String addressLine2,

    @NotBlank(message = "City is required.")
    @Size(max = 100, message = "City must not exceed 100 characters.")
    String city,

    @NotBlank(message = "State is required.")
    @Size(max = 100, message = "State must not exceed 100 characters.")
    String state,

    @NotBlank(message = "Postal code is required.")
    @Size(max = 20, message = "Postal code must not exceed 20 characters.")
    String postalCode,

    @NotBlank(message = "Country is required.")
    @Size(max = 100, message = "Country must not exceed 100 characters.")
    String country,

    @NotNull(message = "Address type is required.")
    AddressType addressType,

    boolean defaultAddress

) {
}
