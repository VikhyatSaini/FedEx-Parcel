package com.example.parcel.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateOrderResponse(String trackingNumber, BigDecimal price, Instant pickupEta){}
