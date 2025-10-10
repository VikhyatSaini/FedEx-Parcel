package com.example.parcel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String fullName;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String role; // "CUSTOMER", "ADMIN", "DRIVER"
}
