package com.example.parcel.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal computePrice(String fromCode, String toCode, double weightKg) {
        double base = 50.0;
        double perKg = 20.0;
        return BigDecimal.valueOf(base + perKg * Math.ceil(weightKg));
    }
}