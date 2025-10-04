package com.example.parcel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender mail;
    @Async
    public void sendPickupEmail(String to, String body){
        var msg = new SimpleMailMessage();
        msg.setTo(to); msg.setSubject("Pickup Scheduled"); msg.setText(body);
        mail.send(msg);
    }
}
