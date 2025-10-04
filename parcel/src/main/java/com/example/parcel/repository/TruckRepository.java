package com.example.parcel.repository;

import com.example.parcel.entity.Truck;
import com.example.parcel.entity.DeliveryNode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TruckRepository extends JpaRepository<Truck, Long> {
    Optional<Truck> findFirstByActiveTrueAndHomeNode(DeliveryNode homeNode);
    Optional<Truck> findByRegNo(String regNo); // <-- Add this line

}
