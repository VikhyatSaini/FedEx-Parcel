package com.example.parcel.service;

import com.example.parcel.dto.AddressDto;
import com.example.parcel.dto.CreateOrderRequest;
import com.example.parcel.dto.CreateOrderResponse;
import com.example.parcel.entity.Address;
import com.example.parcel.entity.Shipment;
import com.example.parcel.entity.User;
import com.example.parcel.repository.DeliveryPlanRepository;
import com.example.parcel.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {
    private final ShipmentRepository repo; private final PlanningService planner;
    private final NotificationService notify; private final PricingService pricing;
    private final DeliveryPlanRepository planRepo;

    @Transactional
    public CreateOrderResponse createOrder(User customer, CreateOrderRequest req){
        Shipment s = new Shipment();
        s.setCustomer(customer);
        s.setItemDescription(req.itemDescription());
        s.setWeightKg(req.weightKg());
        s.setPickupAddress(map(req.pickup()));
        s.setDeliveryAddress(map(req.delivery()));
        s.setTrackingNumber(UUID.randomUUID().toString().replace("-","").substring(0,12).toUpperCase());
        s.setPrice(pricing.computePrice(req.fromNodeCode(), req.toNodeCode(), req.weightKg()));
        repo.save(s);
        var plan = planner.plan(s, req.fromNodeCode(), req.toNodeCode());
        planRepo.save(plan);
        // notify.sendPickupEmail(customer.getEmail(),
        //         "Pickup ETA: " + plan.getPickupEta() + "\nPickup contact: " + plan.getPickupPersonName());        THIS IS TO BE BROUGHT BACK
        return new CreateOrderResponse(s.getTrackingNumber(), s.getPrice(), plan.getPickupEta());
    }

    private Address map(AddressDto d){ var a=new Address();
        a.setName(d.name()); a.setLine1(d.line1()); a.setLine2(d.line2());
        a.setCity(d.city()); a.setState(d.state()); a.setPostalCode(d.postalCode());
        a.setCountryCode(d.countryCode()); a.setPhone(d.phone()); return a;
    }
}