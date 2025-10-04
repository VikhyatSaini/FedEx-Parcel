package com.example.parcel.service;

import com.example.parcel.entity.*;
import com.example.parcel.repository.DriverRepository;
import com.example.parcel.repository.TruckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class PlanningService {
    private final RoutingService routing; private final TruckRepository truckRepo; private final DriverRepository driverRepo;

    @Transactional
    public DeliveryPlan plan(Shipment s, String fromCode, String toCode){
        var nodes = routing.findPath(fromCode, toCode);
        DeliveryPlan plan = new DeliveryPlan(); plan.setShipment(s);
        plan.setPickupEta(Instant.now().plus(4, ChronoUnit.HOURS));
        plan.setPickupPersonName("PickupOps"); plan.setPickupPersonPhone("+91-XXXXXXXXXX");
        // create legs
        for(int i=0;i<nodes.size()-1;i++){
            var leg = new DeliveryLeg();
            leg.setPlan(plan); leg.setFromNode(nodes.get(i)); leg.setToNode(nodes.get(i+1));
            leg.setStatus(LegStatus.WAITING_LOAD);
            // naive assignment: pick active truck at fromNode with capacity >= shipment weight
            var truck = truckRepo.findFirstByActiveTrueAndHomeNode(nodes.get(i))
                    .filter(t->t.getCapacityKg() >= s.getWeightKg()).orElse(null);
            if(truck!=null){
                leg.setTruck(truck);
                var driver = driverRepo.findFirstByActiveTrueAndTruck(truck).orElse(null);
                leg.setDriver(driver);
            }
            plan.getLegs().add(leg);
        }
        s.setStatus(ShipmentStatus.PLANNED);
        return plan;
    }
}
