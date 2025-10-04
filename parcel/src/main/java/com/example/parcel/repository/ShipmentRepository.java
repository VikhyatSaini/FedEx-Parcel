package com.example.parcel.repository;

import com.example.parcel.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    // optional: find by tracking number
    Shipment findByTrackingNumber(String trackingNumber);
}