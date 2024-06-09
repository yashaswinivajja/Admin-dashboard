package com.example.login_password.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {
    @Value("${twilio.accountSid}")
    private String accountSid;
    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.phoneNumber}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void sendOtp(String to, String otp) {
        Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(fromNumber),
                        "Dear User,\n\nYour One-Time Password (OTP) is: " + otp +
                                "\n\nThis OTP is valid for 15 minutes." +
                                "\n\nThank you for using our service auth token.")
                .create();
    }
}
