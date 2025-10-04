package com.example.parcel.service;

import com.example.parcel.dto.*;
import com.example.parcel.entity.DeliveryNode;
import com.example.parcel.entity.RouteEdge;
import com.example.parcel.entity.Truck;
import com.example.parcel.entity.Driver;
import com.example.parcel.repository.DeliveryNodeRepository;
import com.example.parcel.repository.RouteEdgeRepository;
import com.example.parcel.repository.TruckRepository;
import com.example.parcel.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DeliveryNodeRepository nodeRepository;
    private final RouteEdgeRepository edgeRepository;
    private final TruckRepository truckRepository;
    private final DriverRepository driverRepository;
    // You might need a UserRepository here as well to create a user for the driver

    public NodeDto addNode(CreateNodeDto dto) {
        DeliveryNode node = new DeliveryNode();
        node.setCode(dto.getCode());
        node.setCity(dto.getCity());
        nodeRepository.save(node);
        // It's good practice to return the saved entity's data
        return dtoToNodeDto(node);
    }

    public EdgeDto addEdge(CreateEdgeDto dto) {
        DeliveryNode fromNode = nodeRepository.findByCode(dto.getFromNodeCode())
                .orElseThrow(() -> new RuntimeException("From node not found"));
        DeliveryNode toNode = nodeRepository.findByCode(dto.getToNodeCode())
                .orElseThrow(() -> new RuntimeException("To node not found"));

        RouteEdge edge = new RouteEdge();
        edge.setFromNode(fromNode);
        edge.setToNode(toNode);
        edge.setDistanceKm(dto.getDistanceKm());
        edgeRepository.save(edge);
        return dtoToEdgeDto(edge);
    }

    public TruckDto addTruck(CreateTruckDto dto) {
        Truck truck = new Truck();
        truck.setRegNo(dto.getRegNo());
        truck.setCapacityKg(dto.getCapacityKg());
        truckRepository.save(truck);
        return dtoToTruckDto(truck);
    }

    public DriverDto addDriver(CreateDriverDto dto) {
        // Note: This requires a more complex setup to also create a User entity for the driver
        // For now, this is a simplified version.
        Truck assignedTruck = truckRepository.findByRegNo(dto.getTruckRegNo()) // Assumes findByRegNo exists
                .orElseThrow(() -> new RuntimeException("Truck not found"));

        Driver driver = new Driver();
        // driver.setUser(...) // You need to create a User with Role.DRIVER first
        driver.setTruck(assignedTruck);
        driverRepository.save(driver);
        
        DriverDto driverDto = new DriverDto();
        driverDto.setFullName(dto.getFullName());
        driverDto.setTruckRegNo(dto.getTruckRegNo());
        return driverDto;
    }

    // Helper methods to map Entities to DTOs
    private NodeDto dtoToNodeDto(DeliveryNode node) {
        NodeDto dto = new NodeDto();
        dto.setCode(node.getCode());
        dto.setCity(node.getCity());
        return dto;
    }

    private EdgeDto dtoToEdgeDto(RouteEdge edge) {
        EdgeDto dto = new EdgeDto();
        dto.setFromNodeCode(edge.getFromNode().getCode());
        dto.setToNodeCode(edge.getToNode().getCode());
        dto.setDistanceKm(edge.getDistanceKm());
        return dto;
    }
    
    private TruckDto dtoToTruckDto(Truck truck) {
        TruckDto dto = new TruckDto();
        dto.setRegNo(truck.getRegNo());
        dto.setCapacityKg(truck.getCapacityKg());
        return dto;
    }
}