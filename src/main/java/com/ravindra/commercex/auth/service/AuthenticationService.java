package com.ravindra.commercex.auth.service;

import com.ravindra.commercex.auth.dto.request.LoginRequest;
import com.ravindra.commercex.auth.dto.request.LogoutRequest;
import com.ravindra.commercex.auth.dto.request.RefreshRequest;
import com.ravindra.commercex.auth.dto.request.RegisterRequest;
import com.ravindra.commercex.auth.dto.response.LoginResponse;
import com.ravindra.commercex.auth.dto.response.RegisterResponse;
import com.ravindra.commercex.auth.dto.response.UserInfo;
import com.ravindra.commercex.auth.entity.RefreshToken;
import com.ravindra.commercex.auth.entity.Role;
import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.auth.exception.EmailAlreadyExistsException;
import com.ravindra.commercex.auth.exception.RoleNotFoundException;
import com.ravindra.commercex.auth.mapper.UserMapper;
import com.ravindra.commercex.auth.repository.RoleRepository;
import com.ravindra.commercex.auth.repository.UserRepository;
import com.ravindra.commercex.security.jwt.JwtProperties;
import com.ravindra.commercex.security.jwt.JwtService;
import com.ravindra.commercex.security.userdetails.CustomUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;


    public AuthenticationService(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, JwtProperties jwtProperties, RefreshTokenService refreshTokenService) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtProperties = jwtProperties;
        this.refreshTokenService = refreshTokenService;
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

    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
            authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(

                    request.getEmail(),

                    request.getPassword()

                )

            );

        CustomUserDetails principal =
            (CustomUserDetails) authentication.getPrincipal();

        String accessToken =
            jwtService.generateAccessToken(principal);

        RefreshToken refreshToken =
            refreshTokenService.create(
                principal.getUser()
            );


        return LoginResponse.builder()

            .accessToken(accessToken)

            .refreshToken(refreshToken.getToken())

            .tokenType("Bearer")

            .expiresIn(
                jwtProperties.getAccessTokenExpiration()
            )

            .user(
                UserInfo.builder()

                    .id(principal.getId())

                    .email(principal.getEmail())

                    .fullName(principal.getFullName())

                    .roles(principal.getRoleNames())

                    .build()
            )

            .build();

    }

    public LoginResponse refresh(RefreshRequest request){

        RefreshToken refreshToken =
            refreshTokenService.validate(
                request.getRefreshToken()
            );

        User user = refreshToken.getUser();

        CustomUserDetails principal =
            new CustomUserDetails(user);

        String accessToken =
            jwtService.generateAccessToken(principal);

        RefreshToken newRefreshToken =
            refreshTokenService.rotate(refreshToken);

        return LoginResponse.builder()

            .accessToken(accessToken)

            .refreshToken(newRefreshToken.getToken())

            .tokenType("Bearer")

            .expiresIn(jwtProperties.getAccessTokenExpiration())

            .user(

                UserInfo.builder()

                    .id(principal.getId())

                    .email(principal.getEmail())

                    .fullName(principal.getFullName())

                    .roles(principal.getRoleNames())

                    .build()

            )

            .build();

    }

    public void logout(LogoutRequest request){

        RefreshToken token =
            refreshTokenService.validate(
                request.getRefreshToken()
            );

        refreshTokenService.revoke(token);

    }
}
