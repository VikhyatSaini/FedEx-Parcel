package com.example.parcel.repository;

import com.example.parcel.entity.DeliveryLeg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryLegRepository extends JpaRepository<DeliveryLeg, Long> {
}