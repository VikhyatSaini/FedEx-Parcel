package com.example.parcel.controller;

import com.example.parcel.config.AuthFacade;
import com.example.parcel.dto.UserDto;
import com.example.parcel.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthFacade authFacade;

    // This endpoint will return the details of the currently authenticated user
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        try {
            User currentUser = authFacade.currentUser();

            // Map the User entity to a UserDto
            UserDto dto = new UserDto();
            dto.setId(currentUser.getId());
            dto.setEmail(currentUser.getEmail());
            dto.setFullName(currentUser.getFullName());
            dto.setRole(currentUser.getRole().name());

            return ResponseEntity.ok(dto); // Return 200 OK with the user data
        } catch (Exception e) {
            // If authFacade.currentUser() fails, it means no one is logged in.
            // Return a 401 Unauthorized error instead of crashing.
            return ResponseEntity.status(401).build();
        }
    }
}