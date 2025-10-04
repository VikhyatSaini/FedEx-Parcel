package com.example.parcel.controller;

import com.example.parcel.dto.DriverLocationDto;
import com.example.parcel.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/driver") @RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    // Driver app sends live location
    @PostMapping("/locations")
    @PreAuthorize("hasRole('DRIVER')")
    public void updateLocation(@RequestBody DriverLocationDto dto){ driverService.updateLocation(dto); }

    // Ops marks leg loaded; driver gets notified to move
    @PostMapping("/legs/{legId}/loaded")
    @PreAuthorize("hasRole('ADMIN')")
    public void markLoaded(@PathVariable Long legId){ driverService.markLoaded(legId); }
}
