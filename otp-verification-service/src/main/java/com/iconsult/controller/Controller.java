package com.iconsult.controller;
import com.iconsult.dto.VerifyOTPRequestDto;
import com.iconsult.service.impl.OtpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private OtpServiceImpl otpService;

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtpCode (@RequestBody VerifyOTPRequestDto request) {
        return new ResponseEntity<>(this.otpService.verifyOtp(request), HttpStatus.FOUND);
    }

}
