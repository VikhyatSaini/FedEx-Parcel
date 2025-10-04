package com.example.parcel.dto;



public record CreateOrderRequest(
        String itemDescription, double weightKg,
        AddressDto pickup, AddressDto delivery,
        String fromNodeCode, String toNodeCode
) {}

