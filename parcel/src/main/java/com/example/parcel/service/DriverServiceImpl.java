package com.example.parcel.service;

import com.example.parcel.dto.DriverLocationDto;
import com.example.parcel.entity.Driver;
import com.example.parcel.entity.DriverLocation;
import com.example.parcel.entity.LegStatus;
import com.example.parcel.repository.DeliveryLegRepository; // You will need to create this
import com.example.parcel.repository.DriverLocationRepository; // You will need to create this
import com.example.parcel.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverLocationRepository driverLocationRepository;
    private final DeliveryLegRepository deliveryLegRepository;

    @Override
    @Transactional
    public void updateLocation(DriverLocationDto dto) {
        Driver driver = driverRepository.findById(dto.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found with ID: " + dto.getDriverId()));

        DriverLocation location = new DriverLocation();
        location.setDriver(driver);
        location.setLat(dto.getLatitude());
        location.setLon(dto.getLongitude());
        location.setAtTime(Instant.now());

        driverLocationRepository.save(location);
    }

    @Override
    @Transactional
    public void markLoaded(Long legId) {
        var leg = deliveryLegRepository.findById(legId)
                .orElseThrow(() -> new RuntimeException("Delivery leg not found with ID: " + legId));

        leg.setStatus(LegStatus.LOADED);
        deliveryLegRepository.save(leg);

        // You could add a notification here to the driver's app
        System.out.println("Leg " + legId + " has been marked as LOADED. Driver can now depart.");
    }
}