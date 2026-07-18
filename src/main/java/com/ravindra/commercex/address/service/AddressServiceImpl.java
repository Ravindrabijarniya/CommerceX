package com.ravindra.commercex.address.service;

import com.ravindra.commercex.address.dto.request.CreateAddressRequest;
import com.ravindra.commercex.address.dto.request.UpdateAddressRequest;
import com.ravindra.commercex.address.dto.response.AddressResponse;
import com.ravindra.commercex.address.entity.Address;
import com.ravindra.commercex.address.mapper.AddressMapper;
import com.ravindra.commercex.address.repository.AddressRepository;
import com.ravindra.commercex.address.validator.AddressBusinessValidator;
import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.security.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressBusinessValidator addressBusinessValidator;
    private final AddressMapper addressMapper;
    private final CurrentUserService currentUserService;


    @Override
    @Transactional
    public AddressResponse createAddress(CreateAddressRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        boolean shouldBeDefault = request.defaultAddress()
            || addressBusinessValidator.getActiveAddresses(currentUser).isEmpty();

        if (shouldBeDefault) {
            clearCurrentDefaultAddress(currentUser);
        }

        Address address = Address.create(
            currentUser,
            request.fullName(),
            request.phoneNumber(),
            request.addressLine1(),
            request.addressLine2(),
            request.city(),
            request.state(),
            request.postalCode(),
            request.country(),
            request.addressType(),
            shouldBeDefault
        );

        Address savedAddress = addressRepository.save(address);

        return addressMapper.toResponse(savedAddress);
    }


    @Override
    public List<AddressResponse> getAddresses() {

        User currentUser = currentUserService.getCurrentUser();

        return addressBusinessValidator
            .getActiveAddresses(currentUser)
            .stream()
            .map(addressMapper::toResponse)
            .toList();
    }

    @Override
    public AddressResponse getAddress(Long addressId) {

        User currentUser = currentUserService.getCurrentUser();

        Address address = addressBusinessValidator.getAddress(addressId, currentUser);

        return addressMapper.toResponse(address);
    }

    @Override
    public Optional<AddressResponse> getDefaultAddress() {

        User currentUser = currentUserService.getCurrentUser();

        return addressBusinessValidator
            .getDefaultAddress(currentUser)
            .map(addressMapper::toResponse);
    }


    @Override
    @Transactional
    public AddressResponse updateAddress(
        Long addressId,
        UpdateAddressRequest request) {

        User currentUser = currentUserService.getCurrentUser();

        Address address = addressBusinessValidator.getAddress(addressId, currentUser);

        address.update(
            request.fullName(),
            request.phoneNumber(),
            request.addressLine1(),
            request.addressLine2(),
            request.city(),
            request.state(),
            request.postalCode(),
            request.country(),
            request.addressType()
        );

        return addressMapper.toResponse(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId) {

        User currentUser = currentUserService.getCurrentUser();

        Address address = addressBusinessValidator.getAddress(addressId, currentUser);

        address.deactivate();
    }



    @Override
    @Transactional
    public AddressResponse setDefaultAddress(Long addressId) {

        User currentUser = currentUserService.getCurrentUser();

        Address address = addressBusinessValidator.getAddress(addressId, currentUser);

        clearCurrentDefaultAddress(currentUser);

        address.markAsDefault();

        return addressMapper.toResponse(address);
    }



    private void clearCurrentDefaultAddress(User user) {

        addressBusinessValidator
            .getDefaultAddress(user)
            .ifPresent(Address::removeDefault);
    }

}
