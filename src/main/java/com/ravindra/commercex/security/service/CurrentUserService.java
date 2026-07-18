package com.ravindra.commercex.security.service;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.security.userdetails.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface CurrentUserService {

    User getCurrentUser();

}
