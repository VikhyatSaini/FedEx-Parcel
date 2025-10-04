package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class DeliveryLeg {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private DeliveryPlan plan;
    @ManyToOne(optional=false) private DeliveryNode fromNode;
    @ManyToOne(optional=false) private DeliveryNode toNode;
    @ManyToOne private Truck truck;
    @ManyToOne private Driver driver;
    @Enumerated(EnumType.STRING) private LegStatus status; // WAITING_LOAD, LOADED, DEPARTED, ARRIVED
    private Instant departEta; private Instant arriveEta;
}

