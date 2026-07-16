package com.ravindra.commercex.security.jwt;

import com.ravindra.commercex.security.userdetails.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;


    private SecretKey getSigningKey() {

        byte[] keyBytes =
            jwtProperties
                .getSecret()
                .getBytes(StandardCharsets.UTF_8);

        return Keys.hmacShaKeyFor(keyBytes);

    }

    public String generateAccessToken(
        CustomUserDetails userDetails) {

        Date now = new Date();

        Date expiry = new Date(

            now.getTime()

                + jwtProperties.getAccessTokenExpiration()

        );
        return Jwts.builder()

            .setSubject(userDetails.getUsername())

            .setIssuedAt(now)

            .setExpiration(expiry)

            .claim(
                "roles",
                userDetails.getRoleNames()
            )

            .signWith(getSigningKey())

            .compact();
    }

    public String extractUsername(
        String token){
        return extractClaims(token)

            .getSubject();
    }

    private Claims extractClaims(
        String token){
        return Jwts.parser()

            .verifyWith(getSigningKey())

            .build()

            .parseSignedClaims(token)

            .getPayload();
    }

    public Date extractExpiration(
        String token){

        return extractClaims(token)

            .getExpiration();

    }

    private boolean isTokenExpired(
        String token){

        return extractExpiration(token)

            .before(new Date());

    }

    public boolean isTokenValid(

        String token,

        UserDetails userDetails){

        String username =
            extractUsername(token);

        return username.equals(

            userDetails.getUsername()

        )

            &&

            !isTokenExpired(token);

    }

}
