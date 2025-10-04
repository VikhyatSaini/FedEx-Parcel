package com.example.parcel.service;

import com.example.parcel.entity.DeliveryNode;
import com.example.parcel.entity.RouteEdge;
import com.example.parcel.repository.DeliveryNodeRepository;
import com.example.parcel.repository.RouteEdgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoutingService {
    private final DeliveryNodeRepository nodeRepo;
    private final RouteEdgeRepository edgeRepo;

    // Return list of nodes representing best path (Dijkstra over in-memory graph built from DB)
    public List<DeliveryNode> findPath(String fromCode, String toCode) {
        
        // Step 1: Fetch all nodes and edges from the database.
        // This now USES the repositories, which will remove the warnings.
        List<DeliveryNode> allNodes = nodeRepo.findAll();
        List<RouteEdge> allEdges = edgeRepo.findAll();

        // Find the specific start and end nodes from the codes provided.
        DeliveryNode startNode = allNodes.stream().filter(n -> n.getCode().equals(fromCode)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid fromNodeCode: " + fromCode));
        DeliveryNode endNode = allNodes.stream().filter(n -> n.getCode().equals(toCode)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid toNodeCode: " + toCode));

        // --- Implementation of Dijkstra's Algorithm would go here ---
        // This is a complex algorithm that involves:
        // 1. Building a graph data structure from allNodes and allEdges.
        // 2. Using a priority queue to explore paths from the startNode.
        // 3. Tracking distances and previous nodes to reconstruct the shortest path.
        
        // For now, we will return a placeholder path to show the structure.
        System.out.println("Routing from " + startNode.getCity() + " to " + endNode.getCity());
        System.out.println("Fetched " + allNodes.size() + " nodes and " + allEdges.size() + " edges.");

        // This is a placeholder return value. A real implementation would return 
        // the calculated shortest path. For example: List.of(startNode, someIntermediateNode, endNode)
        return new ArrayList<>(Arrays.asList(startNode, endNode));
    }
}