package com.ravindra.commercex.admin.validator;


import com.ravindra.commercex.admin.exception.CustomerNotFoundException;
import com.ravindra.commercex.admin.exception.InvalidCustomerOperationException;
import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.auth.repository.UserRepository;
import com.ravindra.commercex.security.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AdminBusinessValidator {


    private final UserRepository userRepository;

    private final CurrentUserService currentUserService;



    public User validateCustomerExists(
        Long customerId
    ){

        return userRepository.findById(customerId)

            .orElseThrow(
                () ->
                    new CustomerNotFoundException(
                        customerId
                    )
            );

    }



    public void validateEnable(
        User user
    ){

        if(user.isEnabled()){

            throw new InvalidCustomerOperationException(
                "Customer account is already enabled"
            );

        }

    }



    public void validateDisable(
        User user
    ){

        if(!user.isEnabled()){

            throw new InvalidCustomerOperationException(
                "Customer account is already disabled"
            );

        }

    }

    public void validateAdminCannotDisableSelf(
        User targetUser
    ){

        User currentUser =
            currentUserService.getCurrentUser();


        if(
            currentUser.getId()
                .equals(targetUser.getId())
        ){

            throw new InvalidCustomerOperationException(
                "Admin cannot disable own account"
            );

        }

    }

    public void validateCannotDisableAdmin(
        User user
    ){

        if(user.hasRole("ROLE_ADMIN")){

            throw new InvalidCustomerOperationException(
                "Admin account cannot be disabled"
            );

        }

    }

}
