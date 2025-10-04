package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
public class Truck {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique=true) private String regNo;
    private double capacityKg;
    @ManyToOne private DeliveryNode homeNode;
    private boolean active = true;
}