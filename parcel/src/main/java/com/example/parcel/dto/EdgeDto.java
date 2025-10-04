package com.example.parcel.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EdgeDto {
    private String fromNodeCode;
    private String toNodeCode;
    private int distanceKm;
}