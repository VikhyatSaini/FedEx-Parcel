package com.example.parcel.repository;

import com.example.parcel.entity.Driver;
import com.example.parcel.entity.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findFirstByActiveTrueAndTruck(Truck truck);
}
