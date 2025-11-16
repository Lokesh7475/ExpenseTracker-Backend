package com.Lokesh.ExpenseTracker.Security.OTP;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final OTPRepo otpRepo;

    public String generateOTP(String email){

        otpRepo.deleteByEmail(email);

        String generatedOTP = String.valueOf(100000 + new Random().nextInt(900000));

        OTP otp = new OTP();
        otp.setEmail(email);
        otp.setOtp(generatedOTP);

        otpRepo.save(otp);

        return generatedOTP;
    }

    public boolean validateOTP(String email, String userOTP){
        OTP otp = otpRepo.findByEmail(email).orElse(null);

        if(otp == null)
            return false;

        if(otp.getExpiresAt().isBefore(LocalDateTime.now()))
            return false;

        return otp.getOtp().equals(userOTP);
    }
}
