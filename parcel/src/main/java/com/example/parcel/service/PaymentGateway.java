package com.example.parcel.service;

import com.example.parcel.dto.PaymentInitResponse;
import com.example.parcel.entity.Shipment;

public interface PaymentGateway {
    PaymentInitResponse createOrder(Shipment s);
    void capture(String providerPaymentId);
}
