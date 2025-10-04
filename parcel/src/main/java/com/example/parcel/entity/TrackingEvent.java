package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class TrackingEvent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Shipment shipment;
    @Enumerated(EnumType.STRING) private ShipmentStatus status;
    private String description; private String location;
    private Instant atTime;
    @PrePersist void pre(){ atTime = Instant.now(); }
}
