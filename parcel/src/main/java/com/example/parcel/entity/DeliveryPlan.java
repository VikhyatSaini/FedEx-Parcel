package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class DeliveryPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne(optional=false) private Shipment shipment;
    private Instant pickupEta;
    private String pickupPersonName; private String pickupPersonPhone;
    @OneToMany(mappedBy="plan", cascade = CascadeType.ALL) private List<DeliveryLeg> legs = new ArrayList<>();
}
