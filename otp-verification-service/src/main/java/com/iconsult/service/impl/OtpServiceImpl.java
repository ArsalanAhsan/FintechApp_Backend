package com.iconsult.service.impl;

import com.iconsult.dto.VerifyOTPRequestDto;
import com.iconsult.dto.VerifyOtpResponseDto;
import com.iconsult.exception.APIResponse;
import com.iconsult.zeenebel.otpservice.VerifyOTPRequest;
import com.iconsult.zeenebel.otpservice.VerifyOTPResponse;
import com.iconsult.zeenebel.otpservice.VerifyOtpServiceGrpc;
import com.iconsult.zeenebel.userservice.UserServicesGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl {

    @Autowired
    public ModelMapper modelMapper;
    @GrpcClient("otp-service")
    private VerifyOtpServiceGrpc.VerifyOtpServiceBlockingStub otpStub;

    public APIResponse verifyOtp(VerifyOTPRequestDto verifyOTPRequestDto){

        VerifyOTPRequest otpRequest = VerifyOTPRequest.newBuilder()
                .setMobile(verifyOTPRequestDto.getMobile())
                .setOtpCode(verifyOTPRequestDto.getOtpCode())
                .build();

        VerifyOTPResponse otpResponse= this.otpStub.verifyOtp(otpRequest);
        VerifyOtpResponseDto otpResponseDto = this.modelMapper.map(otpResponse,VerifyOtpResponseDto.class);
        return new APIResponse<>(HttpStatus.FOUND.value(),otpResponseDto);

    }


}
