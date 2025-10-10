package com.example.parcel.controller;

import com.example.parcel.config.AuthFacade;
import com.example.parcel.dto.CreateOrderRequest;
import com.example.parcel.dto.CreateOrderResponse;
import com.example.parcel.dto.ShipmentDto;
import com.example.parcel.entity.DeliveryPlan;
import com.example.parcel.entity.Shipment;
import com.example.parcel.repository.ShipmentRepository;
import com.example.parcel.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipments;
    private final AuthFacade auth;
    private final ShipmentRepository shipmentRepository;

    @PostMapping
    public CreateOrderResponse create(@Valid @RequestBody CreateOrderRequest req){
        return shipments.createOrder(auth.currentUser(), req);
    }

    @GetMapping("/{tracking}")
    public ShipmentDto get(@PathVariable String tracking){
        System.out.println("--- Inside GET /api/shipments/{tracking} ---");

        // 1. Find the shipment
        Shipment shipment = shipmentRepository.findByTrackingNumber(tracking);
        if (shipment == null) {
            System.out.println("ERROR: Shipment not found with tracking number: " + tracking);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found");
        }
        System.out.println("Step 1: Shipment found successfully.");

        // 2. Security Check
        if (shipment.getCustomer() == null) {
            System.out.println("FATAL ERROR: The customer for this shipment is null!");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Shipment data is corrupted.");
        }
        if (!shipment.getCustomer().getId().equals(auth.currentUser().getId())) {
            System.out.println("ERROR: Security check failed. User does not own this shipment.");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to view this shipment");
        }
        System.out.println("Step 2: Security check passed.");

        // 3. Get the Delivery Plan (and check if it's null)
        DeliveryPlan plan = shipment.getPlan();
        if (plan == null) {
            System.out.println("FATAL ERROR: The DeliveryPlan for this shipment is null!");
            // Even if the plan is null, we should not crash. We'll return what we have.
        } else {
            System.out.println("Step 3: DeliveryPlan found successfully.");
        }

        // 4. Safely map the data
        System.out.println("Step 4: Mapping data to DTO...");
        LocalDateTime pickupEta = null;
        String pickupPersonName = null;
        if (plan != null && plan.getPickupEta() != null) {
            pickupEta = LocalDateTime.ofInstant(plan.getPickupEta(), ZoneId.systemDefault());
            pickupPersonName = plan.getPickupPersonName();
        }

        ShipmentDto dto = new ShipmentDto(
                shipment.getTrackingNumber(),
                shipment.getItemDescription(),
                shipment.getWeightKg(),
                formatAddress(shipment.getPickupAddress()),
                formatAddress(shipment.getDeliveryAddress()),
                shipment.getPrice(),
                pickupEta,
                pickupPersonName
        );
        System.out.println("Step 5: DTO created successfully. Returning response.");
        return dto;
    }

    private String formatAddress(com.example.parcel.entity.Address address) {
        if (address == null) return "Address not available";
        return String.format("%s, %s, %s", address.getLine1(), address.getCity(), address.getState());
    }
}