package com.ravindra.commercex.profile.service;


import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.profile.dto.ChangePasswordRequest;
import com.ravindra.commercex.profile.dto.UpdateProfileRequest;
import com.ravindra.commercex.profile.dto.ProfileResponse;
import com.ravindra.commercex.profile.mapper.ProfileMapper;
import com.ravindra.commercex.profile.validator.ProfileBusinessValidator;
import com.ravindra.commercex.security.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {


    private final CurrentUserService currentUserService;

    private final ProfileBusinessValidator profileBusinessValidator;

    private final PasswordEncoder passwordEncoder;



    @Transactional(readOnly = true)
    public ProfileResponse getMyProfile(){


        User user =
            currentUserService.getCurrentUser();


        return ProfileMapper.toResponse(user);

    }


    public ProfileResponse updateProfile(
        UpdateProfileRequest request
    ){


        User user =
            currentUserService.getCurrentUser();


        user.updateProfile(

            request.firstName(),

            request.lastName()

        );


        return ProfileMapper.toResponse(user);

    }



    public void changePassword(
        ChangePasswordRequest request
    ){


        User user =
            currentUserService.getCurrentUser();



        profileBusinessValidator
            .validatePasswordChange(
                user,
                request.currentPassword()
            );



        user.changePassword(

            passwordEncoder.encode(
                request.newPassword()
            )

        );

    }

}
