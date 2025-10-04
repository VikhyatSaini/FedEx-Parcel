package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RouteEdge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private DeliveryNode fromNode;
    @ManyToOne(optional=false) private DeliveryNode toNode;
    private int distanceKm;
    private boolean active = true;
}
