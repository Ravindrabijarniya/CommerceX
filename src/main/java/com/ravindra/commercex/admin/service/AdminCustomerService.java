package com.ravindra.commercex.admin.service;


import com.ravindra.commercex.admin.dto.request.UpdateCustomerStatusRequest;
import com.ravindra.commercex.admin.dto.response.AdminCustomerResponse;
import com.ravindra.commercex.admin.mapper.AdminCustomerMapper;
import com.ravindra.commercex.admin.validator.AdminBusinessValidator;
import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AdminCustomerService {


    private final UserRepository userRepository;

    private final AdminBusinessValidator validator;



    @Transactional(readOnly = true)
    public Page<AdminCustomerResponse> getCustomers(
        Pageable pageable
    ){

        return userRepository
            .findAllByOrderByCreatedAtDesc(pageable)
            .map(
                AdminCustomerMapper::toResponse
            );

    }



    @Transactional(readOnly = true)
    public AdminCustomerResponse getCustomer(
        Long customerId
    ){

        User user =
            validator.validateCustomerExists(
                customerId
            );


        return AdminCustomerMapper
            .toResponse(user);

    }



    public AdminCustomerResponse disableCustomer(
        Long customerId
    ){

        User user =
            validator.validateCustomerExists(
                customerId
            );


        validator.validateDisable(user);


        user.disableAccount();


        return AdminCustomerMapper
            .toResponse(user);

    }



    public AdminCustomerResponse enableCustomer(
        Long customerId
    ){

        User user =
            validator.validateCustomerExists(
                customerId
            );


        validator.validateEnable(user);


        user.enableAccount();


        return AdminCustomerMapper
            .toResponse(user);

    }

    public AdminCustomerResponse updateCustomerStatus(
        Long customerId,
        UpdateCustomerStatusRequest request
    ){

        User user =
            validator.validateCustomerExists(customerId);


        if(!request.enabled()){

            validator.validateAdminCannotDisableSelf(user);

            validator.validateDisable(user);

            user.disableAccount();

        }
        else{

            validator.validateEnable(user);

            user.enableAccount();

        }


        return AdminCustomerMapper.toResponse(user);
    }
}
