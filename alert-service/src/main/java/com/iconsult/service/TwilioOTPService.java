package com.iconsult.service;

import com.iconsult.payload.OtpDtoRequest;
import com.iconsult.payload.OtpDtoResponse;
import org.springframework.http.ResponseEntity;

public interface TwilioOTPService {

    public OtpDtoResponse generateOtp(OtpDtoRequest otpDtoRequest);


}

