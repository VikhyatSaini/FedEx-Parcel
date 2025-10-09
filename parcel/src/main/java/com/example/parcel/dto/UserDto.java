package com.example.parcel.dto;

import lombok.Data;

@Data // Lombok annotation for getters, setters, etc.
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private String role;
}