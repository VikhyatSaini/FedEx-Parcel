package com.example.parcel.service;

import com.example.parcel.dto.JwtResponse;
import com.example.parcel.dto.LoginRequest;
import com.example.parcel.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public JwtResponse registerCustomer(RegisterRequest request) {
        // TODO: save user to DB
        // For now we just return a fake JWT
        return new JwtResponse("fake-jwt-for-" + request.getEmail());
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        // TODO: validate user credentials from DB
        // For now we just return a fake JWT
        return new JwtResponse("fake-jwt-for-" + request.getEmail());
    }
}
