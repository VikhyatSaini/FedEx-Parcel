package com.example.parcel.service;


import com.example.parcel.dto.DriverLocationDto;
import org.springframework.stereotype.Service;

@Service
public class DriverServiceImpl implements DriverService {

    @Override
    public void updateLocation(DriverLocationDto dto) {
        // TODO: Save driver location to DB or cache
        System.out.println("Updating driver " + dto.getDriverId() + " location: " + dto.getLatitude() + ", " + dto.getLongitude());
    }

    @Override
    public void markLoaded(Long legId) {
        // TODO: Update route leg status in DB and notify driver
        System.out.println("Leg " + legId + " marked as loaded.");
    }
}
