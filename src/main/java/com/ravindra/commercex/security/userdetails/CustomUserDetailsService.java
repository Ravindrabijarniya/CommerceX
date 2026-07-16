package com.ravindra.commercex.security.userdetails;

import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
    implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(
        String username){

        User user =

            userRepository

                .findByEmail(username)

                .orElseThrow(() ->

                    new UsernameNotFoundException(

                        username

                    ));

        return new CustomUserDetails(user);

    }
}
