package com.example.parcel.service;

import com.example.parcel.dto.*;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public NodeDto addNode(CreateNodeDto dto) {
        // TODO: save node to DB
        NodeDto node = new NodeDto();
        node.setCode(dto.getCode());
        node.setCity(dto.getCity());
        return node;
    }

    public EdgeDto addEdge(CreateEdgeDto dto) {
        // TODO: save edge to DB
        EdgeDto edge = new EdgeDto();
        edge.setFromNodeCode(dto.getFromNodeCode());
        edge.setToNodeCode(dto.getToNodeCode());
        edge.setDistanceKm(dto.getDistanceKm());
        return edge;
    }

    public TruckDto addTruck(CreateTruckDto dto) {
        TruckDto truck = new TruckDto();
        truck.setRegNo(dto.getRegNo());
        truck.setCapacityKg(dto.getCapacityKg());
        return truck;
    }

    public DriverDto addDriver(CreateDriverDto dto) {
        DriverDto driver = new DriverDto();
        driver.setFullName(dto.getFullName());
        driver.setTruckRegNo(dto.getTruckRegNo());
        return driver;
    }
}
