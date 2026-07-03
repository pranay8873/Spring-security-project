package com.spring.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class Jwtutil {
    private static final String ROLE_TAG = "role";
    private static final String ISSUED_DATE_TAG = "issued-date";
    private static final String TOKEN_ISSUER = "TrainingMug";


    @Value("${jwt.validity.accessToken}")
    private Long ACCESS_TOKEN_VALIDITY_DURATION;
    @Value("${jwt.validity.refreshToken}")
    private Long REFRESH_TOKEN_VALIDITY_DURATION;
    @Value("${jwt.secret}")
    private String SECRET;
    // creating sign in key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
    // Generating access token
    public String generateAccessToken(String username, String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE_TAG, role);
        claims.put(ISSUED_DATE_TAG, new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_DURATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    // extracting user nsme from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    // generic claim extractor
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    // extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    // extract expiration
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    // token expiry
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    // token validation
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }


}
