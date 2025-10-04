package com.example.parcel.service;


import com.example.parcel.dto.DriverLocationDto;

public interface DriverService {
    void updateLocation(DriverLocationDto dto);
    void markLoaded(Long legId);
}
