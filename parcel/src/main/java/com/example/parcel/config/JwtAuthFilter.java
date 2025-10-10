package com.example.parcel.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AuthFacade authFacade;

    @Override
protected void doFilterInternal(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull FilterChain filterChain) throws ServletException, IOException {
    
    String path = request.getServletPath();
    
    // ✅ FIRST: Skip JWT for ALL auth endpoints
    if (path.startsWith("/api/auth/")) {
        filterChain.doFilter(request, response);
        return;
    }
    
    // ✅ SECOND: Only process JWT for protected endpoints
    String authHeader = request.getHeader("Authorization");
    String token = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
    }

    // ✅ ONLY validate if token exists
    if (token != null && jwtService.validateToken(token)) {
        var user = authFacade.loadUserByToken(token);
        var auth = jwtService.getAuthentication(user, request);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    filterChain.doFilter(request, response);
}
}