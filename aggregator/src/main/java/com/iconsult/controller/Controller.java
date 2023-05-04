package com.iconsult.controller;

import com.iconsult.dto.UserDto;
import com.iconsult.dto.UserSignInRequestDTO;
import com.iconsult.services.userService;
import com.iconsult.zeenebel.emailservice.EmailOTPServiceGrpc;
import com.iconsult.zeenebel.emailservice.GenerateOTPRequest;
import com.iconsult.zeenebel.emailservice.GenerateOTPResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {
    @Autowired
    public userService userSer;
@GrpcClient("")
    private EmailOTPServiceGrpc.EmailOTPServiceBlockingStub emailStub;

    @Autowired
    public Controller(EmailOTPServiceGrpc.EmailOTPServiceBlockingStub emailStub) {
        this.emailStub = emailStub;
    }

    @GetMapping("showUsers")
    public ResponseEntity<?> showUsers(){
    return new ResponseEntity<>(this.userSer.showUsers(), HttpStatus.FOUND);

}

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody GenerateOTPRequest request) {
        GenerateOTPResponse response = emailStub.generateOTP(request);
        return ResponseEntity.ok("email sent successfully");
    }

@PostMapping("userSignIn")
    public ResponseEntity<?> userSignIn(@RequestBody UserSignInRequestDTO request){
    return new ResponseEntity<>(this.userSer.signInUser(request), HttpStatus.FOUND);
}
    @PostMapping("userSignUP")
    public ResponseEntity<?> userSignUP(@RequestBody UserDto request){
        return new ResponseEntity<>(this.userSer.signUpUser(request), HttpStatus.CREATED);
    }

}
