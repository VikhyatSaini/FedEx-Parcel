package com.example.parcel.dto;


import java.math.BigDecimal;

public record PaymentInitResponse(
        String provider,
        String providerPaymentId,
        BigDecimal amount,
        String paymentUrl
) {}
