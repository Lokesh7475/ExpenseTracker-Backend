package com.Lokesh.ExpenseTracker.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendOTPEmail(String email, String otp){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject("Your OTP Code");
        mailMessage.setText("Your OTP for Expense Tracker: " + otp +
                "\n\nThis OTP is only valid for 60 seconds.\n\nExpense Tracker");

        javaMailSender.send(mailMessage);
    }
}
