package com.iwms.security;

// JWT token generate karna
// Token se email extract karna
// Token se roles extract karna
// Token validate karna
// Token expiry check karna

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "iwms-project-secret-key-for-jwt-authentication-2026";

    private static final long EXPIRATION_TIME =
            24 * 60 * 60 * 1000;

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }

    // ============================
    // GENERATE TOKEN
    // ============================

    public String generateToken(
            String email,
            List<String> roles) {

        Date now = new Date();

        Date expiry = new Date(
                now.getTime() + EXPIRATION_TIME
        );

        return Jwts.builder()
                .subject(email)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    // ============================
    // EXTRACT EMAIL
    // ============================

    public String extractEmail(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // ============================
    // EXTRACT ROLES
    // ============================

    public List<String> extractRoles(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("roles", List.class);
    }

    // ============================
    // VALIDATE TOKEN
    // ============================

    public boolean isTokenValid(
            String token,
            String email) {

        try {

            String tokenEmail = extractEmail(token);

            return tokenEmail.equals(email)
                    && !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }

    // ============================
    // CHECK TOKEN EXPIRY
    // ============================

    private boolean isTokenExpired(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getExpiration()
                .before(new Date());
    }
}