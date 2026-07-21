package com.ravindra.commercex.admin.mapper;


import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.admin.dto.response.AdminCustomerResponse;

import java.util.stream.Collectors;


public final class AdminCustomerMapper {


    private AdminCustomerMapper(){
    }



    public static AdminCustomerResponse toResponse(
        User user
    ){

        return new AdminCustomerResponse(

            user.getId(),

            user.getFirstName(),

            user.getLastName(),

            user.getEmail(),

            user.isEnabled(),

            user.isAccountLocked(),

            user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet()),

            user.getCreatedAt()

        );

    }

}
