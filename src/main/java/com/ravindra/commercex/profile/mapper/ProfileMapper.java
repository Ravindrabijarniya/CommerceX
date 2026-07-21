package com.ravindra.commercex.profile.mapper;


import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.profile.dto.ProfileResponse;

import java.util.stream.Collectors;


public final class ProfileMapper {


    private ProfileMapper() {
    }


    public static ProfileResponse toResponse(User user){

        return new ProfileResponse(

            user.getId(),

            user.getFirstName(),

            user.getLastName(),

            user.getEmail(),

            user.getRoles()
                .stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet()),

            user.getCreatedAt()

        );

    }

}
