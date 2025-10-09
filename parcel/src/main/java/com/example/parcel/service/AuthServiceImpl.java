package com.example.parcel.service;

import com.example.parcel.dto.JwtResponse;
import com.example.parcel.dto.LoginRequest;
import com.example.parcel.dto.RegisterRequest;
import com.example.parcel.entity.Role;
import com.example.parcel.entity.User;
import com.example.parcel.repository.UserRepository;
import com.example.parcel.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Automatically handles the constructor injection
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // In AuthServiceImpl.java

    @Override
    public JwtResponse registerCustomer(RegisterRequest request) {
        // Check if a user with this email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            // If they do, throw a specific, helpful exception
            throw new IllegalStateException("Error: Email is already in use!");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getUsername());
        newUser.setRole(Role.CUSTOMER);

        userRepository.save(newUser);

        return new JwtResponse(jwtService.generateToken(newUser));
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            // If password is correct, generate and return a real JWT
            return new JwtResponse(jwtService.generateToken(user));
        } else {
            // Handle incorrect password
            throw new RuntimeException("Invalid password");
        }
    }
}