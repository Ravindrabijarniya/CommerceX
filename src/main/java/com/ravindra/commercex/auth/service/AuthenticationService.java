package com.ravindra.commercex.auth.service;

import com.ravindra.commercex.auth.dto.request.RegisterRequest;
import com.ravindra.commercex.auth.dto.response.RegisterResponse;
import com.ravindra.commercex.auth.entity.Role;
import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.auth.exception.EmailAlreadyExistsException;
import com.ravindra.commercex.auth.exception.RoleNotFoundException;
import com.ravindra.commercex.auth.mapper.UserMapper;
import com.ravindra.commercex.auth.repository.RoleRepository;
import com.ravindra.commercex.auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationService(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public RegisterResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        Role customerRole = roleRepository
            .findByName("ROLE_CUSTOMER")
            .orElseThrow(() ->
                new RoleNotFoundException("ROLE_CUSTOMER"));

        User user = UserMapper.toEntity(request);

        user.setPassword(
            passwordEncoder.encode(request.getPassword())
        );

        user.getRoles().add(customerRole);

        User savedUser =
            userRepository.save(user);

        return RegisterResponse.builder()
            .id(savedUser.getId())
            .firstName(savedUser.getFirstName())
            .lastName(savedUser.getLastName())
            .email(savedUser.getEmail())
            .message("Registration successful")
            .build();

    }
}
