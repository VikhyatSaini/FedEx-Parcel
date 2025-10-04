package com.example.parcel.service;

import com.example.parcel.dto.PaymentInitResponse;
import com.example.parcel.dto.ProviderCallback;

public interface PaymentService {
    PaymentInitResponse initiate(String trackingNumber, String provider);
    void handleCallback(ProviderCallback payload);
}
