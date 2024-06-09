package com.example.login_password.service;

import com.example.login_password.model.UserOtp;
import com.example.login_password.repository.UserOtpRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {
    @Autowired
    private UserOtpRespository user_otp_repo;

    @Autowired
    private TwilioService twilioService;

    public boolean generateOtp(String phoneNumber) {
        String formattedPhoneNumber = formatPhoneNumber(phoneNumber);

        Optional<UserOtp> user = user_otp_repo.findByPhoneNumberAndOtpAndExpirationTimeAfter(formattedPhoneNumber, null, null);

        if (user.isEmpty()) {
            UserOtp userTemp = new UserOtp();
            LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);
            Random random = new Random();
            int otpValue = 100000 + random.nextInt(900000);
            String otp = String.valueOf(otpValue);

            userTemp.setPhoneNumber(formattedPhoneNumber);
            twilioService.sendOtp(formattedPhoneNumber, otp);
            userTemp.setOtp(otp);
            userTemp.setExpirationTime(expirationTime);
            user_otp_repo.save(userTemp);
            return true;
        } else {
            System.out.println("OTP was already sent...");
            return false;
        }
    }

    public boolean verifyOtp(String phoneNumber, String otp) {
        String formattedPhoneNumber = formatPhoneNumber(phoneNumber);

        Optional<UserOtp> optionalOtp = user_otp_repo.findByPhoneNumberAndOtpAndExpirationTimeAfter(formattedPhoneNumber, otp, LocalDateTime.now());
        if (optionalOtp.isPresent()) {
            user_otp_repo.delete(optionalOtp.get());
            return true;
        }
        return false;
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+91" + phoneNumber;
        }
        return phoneNumber;
    }
}
