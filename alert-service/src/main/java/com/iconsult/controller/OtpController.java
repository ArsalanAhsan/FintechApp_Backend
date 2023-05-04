package com.iconsult.controller;
import com.iconsult.payload.OtpDtoRequest;
import com.iconsult.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/generate-otp")
public class OtpController {

    @Autowired
    private SmsService service;

    @PostMapping
    public ResponseEntity<String> generateOtp(@RequestBody OtpDtoRequest otpDtoRequest){
      this.service.sendSms(otpDtoRequest);
      return new ResponseEntity<>("Otp has been sent successfully !", HttpStatus.ACCEPTED);    }
}
