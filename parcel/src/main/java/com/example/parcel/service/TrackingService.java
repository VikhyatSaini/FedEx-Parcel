package com.example.parcel.service;
import com.example.parcel.dto.TrackingSummary;

import org.springframework.stereotype.Service;

@Service
public class TrackingService {

    public TrackingSummary summary(String trackingNumber) {
        TrackingSummary summary = new TrackingSummary();
        summary.setTrackingNumber(trackingNumber);
        summary.setStatus("In Transit");
        return summary;
    }
}
