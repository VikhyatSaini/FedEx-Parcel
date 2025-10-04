package com.example.parcel.config;


import com.example.parcel.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Secret from application.yml
    private final String jwtSecret = "change-me-please-very-long"; // >= 32 chars
    private final long expirationMs = 120 * 60 * 1000L; // 120 minutes

    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    // Generate JWT token for a user
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key)
                .compact();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Build Authentication object for Spring Security
    public Authentication getAuthentication(User user, HttpServletRequest request) {
        var auth = new UsernamePasswordAuthenticationToken(
                user, null, user.getRole().getAuthorities()
        );
        auth.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource()
                .buildDetails(request));
        return auth;
    }

    // Extract email/subject from token
    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token)
                .getBody().getSubject();
    }
}

