package com.iconsult.service;

import com.iconsult.payload.OtpDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final TwilioOTPService otpService;

    @Autowired
    public SmsService(TwilioOTPService otpService) {
        this.otpService = otpService;
    }

    public void sendSms(OtpDtoRequest otpDtoRequest){
        this.otpService.generateOtp(otpDtoRequest);

    }
}
