package com.iconsult.controller;

import com.iconsult.dto.UserDto;
import com.iconsult.dto.UserSignInRequestDTO;
import com.iconsult.dto.addBeneficiaryRequestDto;
import com.iconsult.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class Controller {
    @Autowired
    public UserServiceImpl userSer;

    @GetMapping("showUsers")
    public ResponseEntity<?> showUsers() {
        return new ResponseEntity<>(this.userSer.showUsers(), HttpStatus.FOUND);

    }

    @PostMapping("userSignIn")
    public ResponseEntity<?> userSignIn(@Valid @RequestBody UserSignInRequestDTO request) {
        return new ResponseEntity<>(this.userSer.signInUser(request), HttpStatus.FOUND);
    }

    @PostMapping("userSignUP")
    public ResponseEntity<?> userSignUP(@Valid @RequestBody UserDto request) {
        return new ResponseEntity<>(this.userSer.signUpUser(request), HttpStatus.CREATED);
    }

    @PostMapping("addBeneficiary")
    public ResponseEntity<?> addBeneficiary(@Valid @RequestBody addBeneficiaryRequestDto request, @RequestHeader ("userId") int userId) {
        return new ResponseEntity<>(this.userSer.addBeneficiary(request,userId), HttpStatus.CREATED);
    }
}
