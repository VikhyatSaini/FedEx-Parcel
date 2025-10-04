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

    @Override
    public JwtResponse registerCustomer(RegisterRequest request) {
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getUsername()); // Assuming username is the full name
        newUser.setRole(Role.CUSTOMER); // Assign a default role

        userRepository.save(newUser);

        // Generate a real JWT for the new user
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