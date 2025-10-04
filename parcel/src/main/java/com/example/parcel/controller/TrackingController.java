package com.example.parcel.controller;

import com.example.parcel.service.TrackingService;
import com.example.parcel.dto.TrackingSummary; 
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/track") @RequiredArgsConstructor
public class TrackingController {
    private final TrackingService tracking;
    @GetMapping("/{trackingNumber}") public TrackingSummary get(@PathVariable String trackingNumber){
        return tracking.summary(trackingNumber);
    }
}
