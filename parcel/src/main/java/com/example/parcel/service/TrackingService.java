package com.example.parcel.service;

import org.springframework.stereotype.Service;

@Service
public class TrackingService {

    public com.example.parcel.service.TrackingSummary summary(String trackingNumber) {
        com.example.parcel.service.TrackingSummary summary = new com.example.parcel.service.TrackingSummary();
        summary.setTrackingNumber(trackingNumber);
        summary.setStatus("In Transit");
        return summary;
    }
}
