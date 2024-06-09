package com.example.login_password.controller;

import com.example.login_password.Response.ApiResponse;
import com.example.login_password.model.User;
import com.example.login_password.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService user_services;

    @Autowired
    private OtpController otp_controller;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> SignUp(@RequestParam String phoneNumber, @RequestParam String password,
                                                      @RequestParam String otp) {
        ResponseEntity<ApiResponse<String>> otpResponse = otp_controller.verifyOtp(phoneNumber, otp);
        if (otpResponse.getStatusCode() == HttpStatus.OK) {
            boolean isUserUpdated = user_services.addUser(phoneNumber, password);
            if (isUserUpdated) {
                return ResponseEntity.ok(new ApiResponse<>("success", "User added/updated successfully.", null));
            } else {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>("error", "Error in creating/updating user.", null));
            }
        } else {
            return otpResponse;
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<String>> updateProfile(@RequestBody User userInfo) {
        boolean isUserUpdated = user_services.updateUser(userInfo);
        if (isUserUpdated) {
            return ResponseEntity.ok(new ApiResponse<>("success", "User updated successfully.", null));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Error in updating user.", null));
        }
    }

    @GetMapping("/profile/{phoneNumber}")
    public ResponseEntity<ApiResponse<User>> viewProfile(@PathVariable String phoneNumber) {
        User user = user_services.viewUser(phoneNumber);
        if (user != null && user.isLoggedin() == true) {
            return ResponseEntity.ok(new ApiResponse<>("success", "User retrieved successfully.", user));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "User not found.", null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestParam String phoneNumber) {
        boolean isLoggedOut = user_services.logout(phoneNumber);
        if (isLoggedOut) {
            return ResponseEntity.ok(new ApiResponse<>("success", "User logged out successfully.", null));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Error in logging out user.", null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> login(@RequestParam String phoneNumber,
                                                        @RequestParam String password) {
        User user = user_services.login(phoneNumber, password);
        if (user != null) {
            return ResponseEntity.ok(new ApiResponse<>("success", "User logged in successfully.", user));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>("error", "Invalid phone number or password.", null));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam String phoneNumber) {
        ResponseEntity<ApiResponse<String>> otpResponse = otp_controller.generateOtp(phoneNumber);
        if (otpResponse.getStatusCode() == HttpStatus.OK) {
            return ResponseEntity.ok(new ApiResponse<>("success", "otp sent successfully.", null));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Error in generating otp.", null));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestParam String phoneNumber,
                                                             @RequestParam String password, @RequestParam String otp) {
        ResponseEntity<ApiResponse<String>> otpResponse = otp_controller.verifyOtp(phoneNumber, otp);
        if (otpResponse.getStatusCode() == HttpStatus.OK) {
            boolean isUserUpdated = user_services.ResetPassword(phoneNumber, password);
            if (isUserUpdated) {
                return ResponseEntity.ok(new ApiResponse<>("success", "User password resetted successfully.", null));
            } else {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>("error", "Error in resetting password.", null));
            }
        } else {
            return otpResponse;
        }
    }
}
