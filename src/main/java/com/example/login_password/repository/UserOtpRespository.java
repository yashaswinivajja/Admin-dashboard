package com.example.login_password.repository;

import com.example.login_password.model.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserOtpRespository extends JpaRepository<UserOtp, Long> {
    Optional<UserOtp> findByPhoneNumberAndOtpAndExpirationTimeAfter(String phoneNumber, String otp, LocalDateTime expirationTime);
}
