package com.OnlineBusBooking.OnlineBus.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OtpService {

    @Autowired
    private JavaMailSender mailSender;

    // In-memory storage: email → OTP
    private final Map<String, String> otpStore = new HashMap<>();

    public boolean generateAndSendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your OnlineBus OTP");
            message.setText("🔐 Your OTP is: " + otp + "\nValid for 5 minutes.\nDo not share it.");
            mailSender.send(message);

            otpStore.put(email, otp);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Failed to send OTP: " + e.getMessage());
            return false;
        }
    }

    public boolean validateOtp(String email, String otp) {
        return otp.equals(otpStore.get(email));
    }

    public void clearOtp(String email) {
        otpStore.remove(email);
    }
}
