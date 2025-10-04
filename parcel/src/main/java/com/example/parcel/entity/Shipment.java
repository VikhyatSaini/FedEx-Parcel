package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Getter
@Setter
public class Shipment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(unique=true, nullable=false) private String trackingNumber; // parcel id
    @ManyToOne(cascade = CascadeType.ALL) private Address pickupAddress;
    @ManyToOne(cascade = CascadeType.ALL) private Address deliveryAddress;
    private String itemDescription; private double weightKg;
    @Enumerated(EnumType.STRING) private ShipmentStatus status;
    private BigDecimal price;
    @ManyToOne private User customer;
    @OneToOne(mappedBy="shipment", cascade = CascadeType.ALL) private DeliveryPlan plan;
    private Instant createdAt; private Instant updatedAt;
    @PrePersist void pre() { createdAt = Instant.now(); updatedAt = createdAt; status = ShipmentStatus.CREATED; }
    @PreUpdate void upd() { updatedAt = Instant.now(); }
}

