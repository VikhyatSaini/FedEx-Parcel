package com.example.parcel.service;

import com.example.parcel.dto.PaymentInitResponse;
import com.example.parcel.dto.ProviderCallback;
import com.example.parcel.entity.Payment;
import com.example.parcel.entity.PaymentStatus;
import com.example.parcel.entity.Shipment;
import com.example.parcel.repository.PaymentRepository; // You will need to create this
import com.example.parcel.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    // In a real app, you'd have a map or factory to select the right gateway
    private final PaymentGateway fakePaymentGateway = new FakePaymentGateway(); 

    @Override
    @Transactional
    public PaymentInitResponse initiate(String trackingNumber, String provider) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber);
        if (shipment == null) {
            throw new RuntimeException("Shipment not found with tracking number: " + trackingNumber);
        }

        // Use a real payment gateway to create an order
        PaymentInitResponse response = fakePaymentGateway.createOrder(shipment);

        // Create and save a payment record in our database
        Payment payment = new Payment();
        payment.setShipment(shipment);
        payment.setProvider(provider);
        payment.setProviderPaymentId(response.providerPaymentId());
        payment.setAmount(response.amount());
        payment.setStatus(PaymentStatus.INITIATED);
        paymentRepository.save(payment);

        return response;
    }

    @Override
    @Transactional
    public void handleCallback(ProviderCallback payload) {
        Payment payment = paymentRepository.findByProviderPaymentId(payload.getPaymentId()) // Assumes this method exists
                .orElseThrow(() -> new RuntimeException("Payment record not found for ID: " + payload.getPaymentId()));

        if ("SUCCESS".equalsIgnoreCase(payload.getStatus())) {
            payment.setStatus(PaymentStatus.CAPTURED);
            // Here you might update the shipment status as well, e.g., to PICKUP_SCHEDULED
        } else {
            payment.setStatus(PaymentStatus.FAILED);
        }
        paymentRepository.save(payment);

        System.out.println("Payment callback handled for payment ID " + payload.getPaymentId() + ". New status: " + payment.getStatus());
    }
}