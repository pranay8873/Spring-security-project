package com.spring.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Jwtutil {

    private static final String ROLE_TAG = "role";
    private static final String TOKEN_ISSUER = "TrainingMug";

    @Value("${jwt.validity.accessToken}")
    private Long ACCESS_TOKEN_VALIDITY_DURATION;
    @Value("${jwt.validity.refreshToken}")
    private Long REFRESH_TOKEN_VALIDITY_DURATION;
    @Value("${jwt.secret}")
    private String SECRET;

    // signing algorithm, built from the shared secret
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET);
    }

    // generating access token
    public String generateAccessToken(String username, String role) {
        return buildToken(username, role, ACCESS_TOKEN_VALIDITY_DURATION);
    }

    // generating refresh token (kept separate so it can carry a longer validity window)
    public String generateRefreshToken(String username, String role) {
        return buildToken(username, role, REFRESH_TOKEN_VALIDITY_DURATION);
    }

    private String buildToken(String username, String role, long validityMillis) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + validityMillis);

        return JWT.create()
                .withIssuer(TOKEN_ISSUER)
                .withSubject(username)
                .withClaim(ROLE_TAG, role)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(getAlgorithm());
    }

    // extracting username from token
    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    // extracting role from token
    public String extractRole(String token) {
        return decodeToken(token).getClaim(ROLE_TAG).asString();
    }

    // extract expiration
    public Date extractExpiration(String token) {
        return decodeToken(token).getExpiresAt();
    }

    // verifies signature + issuer, and decodes the payload
    private DecodedJWT decodeToken(String token) {
        return JWT.require(getAlgorithm())
                .withIssuer(TOKEN_ISSUER)
                .build()
                .verify(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // token validation
    public boolean validateToken(String token, String username) {
        try {
            return extractUsername(token).equals(username) && !isTokenExpired(token);
        } catch (JWTVerificationException e) {
            // covers bad signature, tampered token, wrong issuer, and expired tokens
            return false;
        }
    }
}
