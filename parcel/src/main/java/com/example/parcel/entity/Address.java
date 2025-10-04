package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank private String name;
    @NotBlank private String line1; private String line2;
    @NotBlank private String city; @NotBlank private String state;
    @NotBlank private String postalCode; @NotBlank private String countryCode;
    @NotBlank private String phone;
}
