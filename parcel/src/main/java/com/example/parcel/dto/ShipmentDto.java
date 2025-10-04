package com.example.parcel.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShipmentDto(
        String trackingNumber,
        String itemDescription,
        double weightKg,
        String pickupAddress,
        String deliveryAddress,
        BigDecimal price,
        LocalDateTime pickupEta,
        String pickupPersonName
) {}