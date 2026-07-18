package com.ravindra.commercex.address.service;

import com.ravindra.commercex.address.dto.request.CreateAddressRequest;
import com.ravindra.commercex.address.dto.request.UpdateAddressRequest;
import com.ravindra.commercex.address.dto.response.AddressResponse;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    AddressResponse createAddress(CreateAddressRequest request);

    List<AddressResponse> getAddresses();

    AddressResponse getAddress(Long addressId);

    AddressResponse updateAddress(
        Long addressId,
        UpdateAddressRequest request
    );

    void deleteAddress(Long addressId);

    AddressResponse setDefaultAddress(Long addressId);

    Optional<AddressResponse> getDefaultAddress();

}
