package com.example.parcel.service;

import com.example.parcel.dto.PaymentInitResponse;
import com.example.parcel.dto.ProviderCallback;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentInitResponse initiate(String trackingNumber, String provider) {
        // TODO: integrate with actual payment provider (Razorpay, Stripe, PayPal...)
        // For now we fake it
        return new PaymentInitResponse(
                provider,
                "PAY-" + trackingNumber,  // fake payment id
                BigDecimal.valueOf(100.0),                   // fake amount
                "https://mockpay.com/" + trackingNumber  // fake redirect url
        );
    }

    @Override
    public void handleCallback(ProviderCallback payload) {
        // TODO: update order status in DB based on callback result
        System.out.println("Payment callback received: " + payload);
    }
}
