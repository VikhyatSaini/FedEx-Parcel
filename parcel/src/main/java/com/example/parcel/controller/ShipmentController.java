package com.example.parcel.controller;

import com.example.parcel.config.AuthFacade;
import com.example.parcel.dto.CreateOrderRequest;
import com.example.parcel.dto.CreateOrderResponse;
import com.example.parcel.dto.ShipmentDto;
import com.example.parcel.entity.DeliveryPlan;
import com.example.parcel.entity.Shipment;
import com.example.parcel.repository.ShipmentRepository;
import com.example.parcel.repository.PaymentRepository; // Import this
import com.example.parcel.entity.Payment;
import com.example.parcel.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService shipments;
    private final AuthFacade auth;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;

    // --- THIS IS THE REORDERED PART ---

    // The MORE SPECIFIC path comes FIRST
    @GetMapping("/my-shipments")
    public List<ShipmentDto> getMyShipments() {
        Long currentUserId = auth.currentUser().getId();
        List<Shipment> myShipments = shipmentRepository.findByCustomerId(currentUserId);
        return myShipments.stream().map(this::mapShipmentToDto).collect(Collectors.toList());
    }

    // The MORE GENERAL path with a variable comes SECOND
    @GetMapping("/{tracking}")
    public ShipmentDto get(@PathVariable String tracking) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(tracking);
        if (shipment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipment not found with tracking number: " + tracking);
        }
        // ... (rest of the method is the same)
        return mapShipmentToDto(shipment);
    }

    // --- END OF REORDERED PART ---


    @PostMapping
    public CreateOrderResponse create(@Valid @RequestBody CreateOrderRequest req){
        return shipments.createOrder(auth.currentUser(), req);
    }

    // --- HELPER METHODS ---

    private ShipmentDto mapShipmentToDto(Shipment shipment) {
        DeliveryPlan plan = shipment.getPlan();
        LocalDateTime pickupEta = null;
        String pickupPersonName = null;
        if (plan != null && plan.getPickupEta() != null) {
            pickupEta = LocalDateTime.ofInstant(plan.getPickupEta(), ZoneId.systemDefault());
            pickupPersonName = plan.getPickupPersonName();
        }
        String status = paymentRepository.findFirstByShipmentIdOrderByCreatedAtDesc(shipment.getId())
                .map(payment -> payment.getStatus().name()) // Get the status name if payment exists
                .orElse("NOT_INITIATED");
        return new ShipmentDto(
                shipment.getTrackingNumber(),
                shipment.getItemDescription(),
                shipment.getWeightKg(),
                formatAddress(shipment.getPickupAddress()),
                formatAddress(shipment.getDeliveryAddress()),
                shipment.getPrice(),
                pickupEta,
                pickupPersonName,
                status
        );
    }

    private String formatAddress(com.example.parcel.entity.Address address) {
        if (address == null) return "Address not available";
        return String.format("%s, %s, %s", address.getLine1(), address.getCity(), address.getState());
    }
}