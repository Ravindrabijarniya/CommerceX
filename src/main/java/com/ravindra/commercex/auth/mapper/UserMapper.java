package com.ravindra.commercex.auth.mapper;

import com.ravindra.commercex.auth.dto.request.RegisterRequest;
import com.ravindra.commercex.auth.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegisterRequest request) {

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return user;
    }

}
