package com.example.parcel.dto;

import lombok.Data;

@Data
public class ProviderCallback {
    private String paymentId;
    private String status; // e.g. "SUCCESS", "FAILED"
    private String trackingNumber;
}
