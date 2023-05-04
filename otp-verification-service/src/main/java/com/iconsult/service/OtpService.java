package com.iconsult.service;

import com.iconsult.entity.Otp;
import com.iconsult.repository.OtpRepository;
import com.iconsult.zeenebel.Constants.EmailOtp;
import com.iconsult.zeenebel.otpservice.VerifyOTPRequest;
import com.iconsult.zeenebel.otpservice.VerifyOTPResponse;
import com.iconsult.zeenebel.otpservice.VerifyOtpServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;



@GrpcService
public class OtpService extends VerifyOtpServiceGrpc.VerifyOtpServiceImplBase {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    public ModelMapper modelMapper;

    @GrpcClient("otp-service")
    private VerifyOtpServiceGrpc.VerifyOtpServiceBlockingStub verifyOtpServiceBlockingStub;

    @Override
    public void verifyOtp(VerifyOTPRequest request, StreamObserver<VerifyOTPResponse> responseObserver) {

        String mobile = request.getMobile();
        String otpCode = request.getOtpCode();

        Otp latestOtp= otpRepository.findTopByMobileNumberOrderByTimestampDesc(mobile);
        System.out.println(latestOtp);

        if(latestOtp != null && latestOtp.getOtpCode().equals(otpCode)){
            long timestamp= latestOtp.getTimestamp();

            if(System.currentTimeMillis() - timestamp <=30000){
                Otp otp =this.otpRepository.findTopByMobileNumberOrderByTimestampDesc(mobile);
                this.otpRepository.delete(otp);
                VerifyOTPResponse builder = VerifyOTPResponse.newBuilder()
                        .setEmailOtpResponse(EmailOtp.Otp_Verified_Successfully)
                        .build();
                responseObserver.onNext(builder);
            }
            else {
                Otp otp =this.otpRepository.findTopByMobileNumberOrderByTimestampDesc(mobile);
                this.otpRepository.delete(otp);
                VerifyOTPResponse builder = VerifyOTPResponse.newBuilder()
                        .setEmailOtpResponse(EmailOtp.Otp_Verification_Time_Out)
                        .build();
                responseObserver.onNext(builder);
            }
        }
        else{
            VerifyOTPResponse builder = VerifyOTPResponse.newBuilder()
                    .setEmailOtpResponse(EmailOtp.Otp_Verification_Failed)
                    .build();
            responseObserver.onNext(builder);

        }
        responseObserver.onCompleted();
    }

}
