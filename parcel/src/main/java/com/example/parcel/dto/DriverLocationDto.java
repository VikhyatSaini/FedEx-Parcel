package com.example.parcel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverLocationDto {
    private Long driverId;
    private double latitude;
    private double longitude;
}
