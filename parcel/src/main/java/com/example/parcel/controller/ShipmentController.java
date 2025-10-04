package com.example.parcel.controller;

import com.example.parcel.config.AuthFacade;
import com.example.parcel.dto.CreateOrderRequest;
import com.example.parcel.dto.CreateOrderResponse;
import com.example.parcel.dto.ShipmentDto;
import com.example.parcel.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipments") @RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipments; private final AuthFacade auth;

    @PostMapping
    public CreateOrderResponse create(@Valid @RequestBody CreateOrderRequest req){
        return shipments.createOrder(auth.currentUser(), req);
    }

    @GetMapping("/{tracking}")
    public ShipmentDto get(@PathVariable String tracking){ /* return shipment + plan */ return null; }
}