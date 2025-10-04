package com.example.parcel.config;

import com.example.parcel.entity.User;
import com.example.parcel.repository.UserRepository; // You will need to create this repository interface
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;


@Service
@RequiredArgsConstructor // Use this for clean constructor injection
public class AuthFacade {

    private final UserRepository userRepo;
    private final JwtService jwtService;

    // This method is fine, but can be improved slightly
    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }
        // It's better to throw an exception if the user is required for an operation
        throw new IllegalStateException("No authenticated user found in security context.");
    }

    // This is the secure implementation you must use
    public User loadUserByToken(String token) {
        String email = jwtService.extractEmail(token);
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
    }
}