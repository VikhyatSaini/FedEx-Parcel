package com.example.parcel.service;

import com.example.parcel.dto.JwtResponse;
import com.example.parcel.dto.LoginRequest;
import com.example.parcel.dto.RegisterRequest;
import com.example.parcel.entity.Role;
import com.example.parcel.entity.User;
import com.example.parcel.exception.UserAlreadyExistsException;
import com.example.parcel.exception.UserNotFoundException;
import com.example.parcel.exception.InvalidCredentialsException;
import com.example.parcel.repository.UserRepository;
import com.example.parcel.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtResponse registerCustomer(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email is already in use: " + request.getEmail());
        }
        User newUser = createUser(request, Role.CUSTOMER);
        return new JwtResponse(jwtService.generateToken(newUser));
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid password for user: " + request.getEmail());
        }

        return new JwtResponse(jwtService.generateToken(user));
    }

    @Override
    public JwtResponse registerAdmin(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email is already in use: " + request.getEmail());
        }
        User newUser = createUser(request, Role.ADMIN);
        return new JwtResponse(jwtService.generateToken(newUser));
    }

    @Override
    public JwtResponse registerDriver(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email is already in use: " + request.getEmail());
        }
        User newUser = createUser(request, Role.DRIVER);
        return new JwtResponse(jwtService.generateToken(newUser));
    }

    private User createUser(RegisterRequest request, Role role) {
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setFullName(request.getFullName());
        newUser.setRole(role);
        return userRepository.save(newUser);
    }
}