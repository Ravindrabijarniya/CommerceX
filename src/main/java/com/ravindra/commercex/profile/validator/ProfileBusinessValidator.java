package com.ravindra.commercex.profile.validator;


import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.profile.exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ProfileBusinessValidator {


    private final PasswordEncoder passwordEncoder;



    public void validatePasswordChange(
        User user,
        String currentPassword
    ){

        boolean matches =
            passwordEncoder.matches(
                currentPassword,
                user.getPassword()
            );


        if(!matches){

            throw new IncorrectPasswordException();

        }

    }


}
