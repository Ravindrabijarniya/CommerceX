package com.ravindra.commercex.address.exception;

import com.ravindra.commercex.common.exception.ResourceNotFoundException;

public class AddressNotFoundException extends ResourceNotFoundException {

    public AddressNotFoundException(Long id) {
        super("Address not found with id: " + id);
    }

}
