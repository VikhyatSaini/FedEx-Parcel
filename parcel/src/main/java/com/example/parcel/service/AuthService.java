package com.example.parcel.service;

import com.example.parcel.dto.JwtResponse;
import com.example.parcel.dto.LoginRequest;
import com.example.parcel.dto.RegisterRequest;

public interface AuthService {
    JwtResponse registerCustomer(RegisterRequest request);
    JwtResponse login(LoginRequest request);
    JwtResponse registerAdmin(RegisterRequest request);
    JwtResponse registerDriver(RegisterRequest request);
}
