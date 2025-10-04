package com.example.parcel.controller;

import com.example.parcel.dto.PaymentInitResponse;
import com.example.parcel.dto.ProviderCallback;
import com.example.parcel.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments") @RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public PaymentInitResponse create(@RequestParam String trackingNumber){
        return paymentService.initiate(trackingNumber, "FAKEPAY");
    }

    @PostMapping("/callback") // webhook simulation
    public void callback(@RequestBody ProviderCallback payload){
        paymentService.handleCallback(payload);
    }
}