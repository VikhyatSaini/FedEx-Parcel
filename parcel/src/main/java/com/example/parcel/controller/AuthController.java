package com.example.parcel.controller;

import com.example.parcel.dto.JwtResponse;
import com.example.parcel.dto.LoginRequest;
import com.example.parcel.dto.RegisterRequest;
import com.example.parcel.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService; // Fixed: consistent name
    
    @PostMapping("/register")
    public JwtResponse register(@Valid @RequestBody RegisterRequest request) { 
        return authService.registerCustomer(request); 
    }
    
    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest request) { 
        return authService.login(request); 
    }
    
    @PostMapping("/register-admin")
    public JwtResponse registerAdmin(@Valid @RequestBody RegisterRequest request) {
        return authService.registerAdmin(request);
    }

    @PostMapping("/register-driver") 
    public JwtResponse registerDriver(@Valid @RequestBody RegisterRequest request) {
        return authService.registerDriver(request);
    }
}