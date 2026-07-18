package com.ravindra.commercex.address.validator;

import com.ravindra.commercex.address.entity.Address;
import com.ravindra.commercex.address.exception.AddressNotFoundException;
import com.ravindra.commercex.address.repository.AddressRepository;
import com.ravindra.commercex.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddressBusinessValidator {

    private final AddressRepository addressRepository;


    public Address getAddress(Long addressId, User user) {

        return addressRepository
            .findByIdAndUserAndActiveTrue(addressId, user)
            .orElseThrow(() -> new AddressNotFoundException(addressId));
    }

    public List<Address> getActiveAddresses(User user) {

        return addressRepository
            .findAllByUserAndActiveTrueOrderByCreatedAtAsc(user);
    }

    public Optional<Address> getDefaultAddress(User user) {

        return addressRepository
            .findByUserAndDefaultAddressTrueAndActiveTrue(user);
    }


//    public void validateAddressExists(Long addressId, User user) {
//
//        if (!addressRepository.existsByIdAndUserAndActiveTrue(addressId, user)) {
//            throw new AddressNotFoundException(addressId);
//        }
//    }

}
