package com.example.parcel.service;

import com.example.parcel.dto.*;
import com.example.parcel.entity.DeliveryNode;
import com.example.parcel.entity.Driver;
import com.example.parcel.entity.Role;
import com.example.parcel.entity.RouteEdge;
import com.example.parcel.entity.Truck;
import com.example.parcel.entity.User;
import com.example.parcel.repository.DeliveryNodeRepository;
import com.example.parcel.repository.DriverRepository;
import com.example.parcel.repository.RouteEdgeRepository;
import com.example.parcel.repository.TruckRepository;
import com.example.parcel.repository.UserRepository; // <-- THE MISSING IMPORT
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final DeliveryNodeRepository nodeRepository;
    private final RouteEdgeRepository edgeRepository;
    private final TruckRepository truckRepository;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    @Transactional
    public NodeDto addNode(CreateNodeDto dto) {
        if (nodeRepository.findByCode(dto.getCode()).isPresent()) {
            throw new IllegalStateException("Node with code already exists");
        }
        DeliveryNode node = new DeliveryNode();
        node.setCode(dto.getCode());
        node.setCity(dto.getCity());
        node = nodeRepository.save(node);
        return dtoToNodeDto(node);
    }

    @Transactional
    public EdgeDto addEdge(CreateEdgeDto dto) {
        DeliveryNode fromNode = nodeRepository.findByCode(dto.getFromNodeCode()).orElseThrow();
        DeliveryNode toNode = nodeRepository.findByCode(dto.getToNodeCode()).orElseThrow();
        RouteEdge edge = new RouteEdge();
        edge.setFromNode(fromNode);
        edge.setToNode(toNode);
        edge.setDistanceKm(dto.getDistanceKm());
        edge = edgeRepository.save(edge);
        return dtoToEdgeDto(edge);
    }

    @Transactional
    public TruckDto addTruck(CreateTruckDto dto) {
        Truck truck = new Truck();
        truck.setRegNo(dto.getRegNo());
        truck.setCapacityKg(dto.getCapacityKg());
        truck = truckRepository.save(truck);
        return dtoToTruckDto(truck);
    }

    @Transactional
    public DriverDto addDriver(CreateDriverDto dto) {
        Truck truck = truckRepository.findByRegNo(dto.getTruckRegNo())
                .orElseThrow(() -> new RuntimeException("Truck with registration number not found: " + dto.getTruckRegNo()));

        User user = userRepository.findByFullName(dto.getFullName())
                .filter(u -> u.getRole() == Role.DRIVER)
                .orElseThrow(() -> new RuntimeException("A user with role DRIVER and full name not found: " + dto.getFullName()));

        Driver driver = new Driver();
        driver.setUser(user);
        driver.setTruck(truck);
        driver.setActive(true);
        driverRepository.save(driver);

        DriverDto driverDto = new DriverDto();
        driverDto.setFullName(user.getFullName());
        driverDto.setTruckRegNo(truck.getRegNo());
        return driverDto;
    }

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