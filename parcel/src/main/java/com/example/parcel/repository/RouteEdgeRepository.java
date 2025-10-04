package com.example.parcel.repository;

import com.example.parcel.entity.RouteEdge;
import com.example.parcel.entity.DeliveryNode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RouteEdgeRepository extends JpaRepository<RouteEdge, Long> {
    List<RouteEdge> findByFromNode(DeliveryNode fromNode);
}
