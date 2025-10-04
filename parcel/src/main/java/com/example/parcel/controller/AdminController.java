package com.example.parcel.controller;


import com.example.parcel.dto.*;
import com.example.parcel.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin") @RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService admin;
    @PostMapping("/nodes") public NodeDto addNode(@RequestBody CreateNodeDto d){ return admin.addNode(d); }
    @PostMapping("/edges") public EdgeDto addEdge(@RequestBody CreateEdgeDto d){ return admin.addEdge(d); }
    @PostMapping("/trucks") public TruckDto addTruck(@RequestBody CreateTruckDto d){ return admin.addTruck(d); }
    @PostMapping("/drivers") public DriverDto addDriver(@RequestBody CreateDriverDto d){ return admin.addDriver(d); }
}