package com.example.parcel.config;

import com.example.parcel.entity.User;
import org.springframework.stereotype.Component;

//package com.example.parcel.config;
//
//
//import com.example.parcel.entity.User;
//import com.example.parcel.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AuthFacade {
//
//    private final UserRepository userRepo;
//    private final JwtService jwtService;
//
//    // Get currently authenticated user
//    public User currentUser() {
//        var auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !(auth.getPrincipal() instanceof User)) {
//            throw new RuntimeException("No authenticated user found");
//        }
//        return (User) auth.getPrincipal();
//    }
//
//    // Load user from token
//    public User loadUserByToken(String token) {
//        String email = jwtService.extractEmail(token);
//        return userRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found for email: " + email));
//    }
//}
//
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthFacade {

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }

        return null; // or throw an exception if user must always be logged in
    }
    public User loadUserByToken(String token) {
        User u = new User();
        u.setId(1L);
        u.setEmail("test@example.com");
        return u;
    }
}


