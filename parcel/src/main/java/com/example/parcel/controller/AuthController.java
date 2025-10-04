package com.example.parcel.controller;

import com.example.parcel.dto.JwtResponse;
import com.example.parcel.dto.LoginRequest;
import com.example.parcel.dto.RegisterRequest;
import com.example.parcel.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
    private final AuthService auth;
    @PostMapping("/register") public JwtResponse register(@Valid @RequestBody RegisterRequest r){ return auth.registerCustomer(r); }
    @PostMapping("/login")    public JwtResponse login(@Valid @RequestBody LoginRequest r){ return auth.login(r); }
}
