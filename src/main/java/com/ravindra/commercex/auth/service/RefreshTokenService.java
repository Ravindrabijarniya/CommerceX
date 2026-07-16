package com.ravindra.commercex.auth.service;

import com.ravindra.commercex.auth.entity.RefreshToken;
import com.ravindra.commercex.auth.entity.User;
import com.ravindra.commercex.auth.repository.RefreshTokenRepository;
import com.ravindra.commercex.security.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    private final JwtProperties jwtProperties;

    public RefreshToken create(User user) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setUser(user);

        refreshToken.setExpiresAt(
            Instant.now().plusMillis(
                jwtProperties.getRefreshTokenExpiration()
            )
        );

        refreshToken.setRevoked(false);

        return repository.save(refreshToken);

    }

}
