package com.example.parcel.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

public enum Role {
    CUSTOMER, // Renamed from USER and now matches AuthServiceImpl
    ADMIN,
    DRIVER;   // Added so your SecurityConfig works correctly

    public List<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}