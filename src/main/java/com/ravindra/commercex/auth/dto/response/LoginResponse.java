package com.ravindra.commercex.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class LoginResponse {

    private Long id;

    private String email;

    private String fullName;

    private Set<String> roles;

    private String message;

}
