package com.iconsult.services.impl;

import com.iconsult.dto.UserDto;
import com.iconsult.dto.UserServiceResponseDTO;
import com.iconsult.dto.UserSignInRequestDTO;
import com.iconsult.dto.addBeneficiaryRequestDto;
import com.iconsult.response.APIResponse;
import com.iconsult.zeenebel.userservice.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {
    @Autowired
    public ModelMapper modelMapper;
    @GrpcClient("user-service")
    private UserServicesGrpc.UserServicesBlockingStub userStub;

    public APIResponse showUsers() {
        showUserRequest request = showUserRequest.newBuilder().build();

        showUserResponse response = this.userStub.showUsers(request);
        List<UserDto> list = response.getUsersList().stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return new APIResponse(HttpStatus.OK.value(), list);
    }

    public APIResponse signInUser( UserSignInRequestDTO requestDTO) {
        CnicCredentials cnicCredentials = CnicCredentials.newBuilder().setCnicNumber(requestDTO.getCnic()).setPassword(requestDTO.getPass()).build();
        userSignInRequest request = userSignInRequest.newBuilder().setCcr(cnicCredentials).
                build();
        userResponse response = this.userStub.userSignIn(request);
        UserServiceResponseDTO userServiceResponseDTO = this.modelMapper.map(response,UserServiceResponseDTO.class);
        return new APIResponse<>(HttpStatus.FOUND.value(),userServiceResponseDTO);
    }


    public APIResponse signUpUser( @Valid UserDto request) {

    userDto user = userDto.newBuilder().setDOB(request.getDob()).setCnic(request.getCnic()).setEmail(request.getEmail())
            .setFirstName(request.getFirstName()).setLastName(request.getLastName()).setPhoneNumber(request.getPhoneNumber()).setPassword(request.getPassword()).build();
    userResponse response = this.userStub.userSignUp(user);
    UserServiceResponseDTO userServiceResponseDTO = this.modelMapper.map(response, UserServiceResponseDTO.class);
    return new APIResponse<>(HttpStatus.CREATED.value(), userServiceResponseDTO);

    }

    public APIResponse addBeneficiary(@Valid addBeneficiaryRequestDto request,int userId) {
        addBeneficiaryRequest beneficiaryRequest = addBeneficiaryRequest.newBuilder()
                .setAccountNum(request.getAccountNum()).setUserId(String.valueOf(userId))
                .setAlias(request.getAlias()).setBank(request.getBank()).build();
        userResponse response = this.userStub.addBeneficiary(beneficiaryRequest);
        UserServiceResponseDTO userServiceResponseDTO = this.modelMapper.map(response, UserServiceResponseDTO.class);
        return new APIResponse<>(HttpStatus.CREATED.value(),userServiceResponseDTO);
    }


}
