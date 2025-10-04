package com.example.parcel.repository;

import com.example.parcel.entity.DeliveryNode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DeliveryNodeRepository extends JpaRepository<DeliveryNode, Long> {
    Optional<DeliveryNode> findByCode(String code);
}
