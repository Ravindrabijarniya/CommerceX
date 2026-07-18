package com.ravindra.commercex.security.service;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public User getCurrentUser() {

        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
            !(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {

            throw new IllegalStateException("No authenticated user found.");
        }

        return userDetails.getUser();
    }

}
