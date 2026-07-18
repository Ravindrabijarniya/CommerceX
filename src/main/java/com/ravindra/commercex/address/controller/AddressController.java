package com.ravindra.commercex.address.controller;

import com.ravindra.commercex.address.dto.request.CreateAddressRequest;
import com.ravindra.commercex.address.dto.request.UpdateAddressRequest;
import com.ravindra.commercex.address.dto.response.AddressResponse;
import com.ravindra.commercex.address.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;


    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(
        @Valid @RequestBody CreateAddressRequest request) {

        AddressResponse response = addressService.createAddress(request);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }


    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddresses() {

        return ResponseEntity.ok(
            addressService.getAddresses()
        );
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(
        @PathVariable Long addressId) {

        return ResponseEntity.ok(
            addressService.getAddress(addressId)
        );
    }

    @GetMapping("/default")
    public ResponseEntity<AddressResponse> getDefaultAddress() {

        Optional<AddressResponse> response =
            addressService.getDefaultAddress();

        return response
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.noContent().build());
    }


    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
        @PathVariable Long addressId,
        @Valid @RequestBody UpdateAddressRequest request) {

        return ResponseEntity.ok(
            addressService.updateAddress(addressId, request)
        );
    }


    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
        @PathVariable Long addressId) {

        addressService.deleteAddress(addressId);

        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{addressId}/default")
    public ResponseEntity<AddressResponse> setDefaultAddress(
        @PathVariable Long addressId) {

        return ResponseEntity.ok(
            addressService.setDefaultAddress(addressId)
        );
    }

}
