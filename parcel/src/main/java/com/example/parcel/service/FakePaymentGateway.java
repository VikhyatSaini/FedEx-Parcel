package com.example.parcel.service;

import com.example.parcel.dto.PaymentInitResponse;
import com.example.parcel.entity.Shipment;
import org.springframework.stereotype.Service;

@Service
public class FakePaymentGateway implements PaymentGateway {
    public PaymentInitResponse createOrder(Shipment s){
        return new PaymentInitResponse("FAKEPAY", "PPID-"+s.getTrackingNumber(), s.getPrice(), "https://mock.pay/"+s.getTrackingNumber());
    }
    public void capture(String id){ /* no-op */ }
}
