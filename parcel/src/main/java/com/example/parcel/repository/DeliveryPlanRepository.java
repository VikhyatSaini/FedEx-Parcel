package com.example.parcel.repository;

import com.example.parcel.entity.DeliveryPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPlanRepository extends JpaRepository<DeliveryPlan, Long> {
}