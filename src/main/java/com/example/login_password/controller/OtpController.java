package com.example.login_password.controller;

import com.example.login_password.Response.ApiResponse;
import com.example.login_password.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OtpController {
    @Autowired
    private OtpService otpService;

    @PostMapping("/generate-otp")
    public ResponseEntity<ApiResponse<String>> generateOtp(@RequestParam String phoneNumber) {
        boolean isOtpGenerated = otpService.generateOtp(phoneNumber);
        if (isOtpGenerated) {
            return ResponseEntity.ok(new ApiResponse<>("success", "OTP has been sent successfully. It will be active for 15 minutes.", null));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "OTP was already sent. Please try again later.", null));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {
        boolean isOtpVerified = otpService.verifyOtp(phoneNumber, otp);
        if (isOtpVerified) {
            return ResponseEntity.ok(new ApiResponse<>("success", "OTP verified successfully.", null));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Invalid OTP or OTP expired.", null));
        }
    }
}
