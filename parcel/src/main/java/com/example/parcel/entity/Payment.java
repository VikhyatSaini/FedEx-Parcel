package com.example.parcel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) private Shipment shipment;
    private String provider; private String providerPaymentId; private BigDecimal amount;
    @Enumerated(EnumType.STRING) private PaymentStatus status;
    private Instant createdAt;
    @PrePersist void pre(){ createdAt = Instant.now(); }
}

