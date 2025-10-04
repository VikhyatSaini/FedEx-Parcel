package com.example.parcel.service;

import com.example.parcel.entity.DeliveryNode;
import com.example.parcel.repository.DeliveryNodeRepository;
import com.example.parcel.repository.RouteEdgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutingService {
    private final DeliveryNodeRepository nodeRepo; private final RouteEdgeRepository edgeRepo;

    // Return list of nodes representing best path (Dijkstra over in-memory graph built from DB)
    public List<DeliveryNode> findPath(String fromCode, String toCode) { /* implement dijkstra */ return List.of(); }
}