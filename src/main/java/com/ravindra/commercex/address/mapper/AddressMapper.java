package com.ravindra.commercex.address.mapper;

import com.ravindra.commercex.address.dto.response.AddressResponse;
import com.ravindra.commercex.address.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressResponse toResponse(Address address);

}
