package com.ravindra.commercex.security.service;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.auth.repository.UserRepository;
import com.ravindra.commercex.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getCurrentUser() {

        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal() instanceof CustomUserDetails userDetails)) {
            throw new IllegalStateException("No authenticated user found.");
        }

        return userRepository.findById(userDetails.getId())
            .orElseThrow(() -> new IllegalStateException("Authenticated user not found."));
    }

}
