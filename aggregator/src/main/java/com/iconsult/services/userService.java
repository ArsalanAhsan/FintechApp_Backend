package com.iconsult.services;

import com.iconsult.dto.UserDto;
import com.iconsult.dto.UserServiceResponseDTO;
import com.iconsult.dto.UserSignInRequestDTO;
import com.iconsult.zeenebel.userservice.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class userService {
    @Autowired
    public ModelMapper modelMapper;
    @GrpcClient("user-service")
    private UserServicesGrpc.UserServicesBlockingStub userStub;

    public List<?> showUsers() {
        showUserRequest request = showUserRequest.newBuilder().build();

        showUserResponse response = this.userStub.showUsers(request);
        List<UserDto> list = response.getUsersList().stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return list;
    }

    public UserServiceResponseDTO signInUser(UserSignInRequestDTO requestDTO) {
        CnicCredentials cnicCredentials = CnicCredentials.newBuilder().setCnicNumber(String.valueOf(requestDTO.getCnic())).setPassword(requestDTO.getPass()).build();
        userSignInRequest userSignInRequest = com.iconsult.zeenebel.userservice.userSignInRequest.newBuilder().setCcr(cnicCredentials).
                build();
        userResponse response = this.userStub.userSignIn(userSignInRequest);
        UserServiceResponseDTO userServiceResponseDTO = new UserServiceResponseDTO();
        userServiceResponseDTO.setStatus(String.valueOf(response.getStat()));
        userServiceResponseDTO.setDescription(response.getDescription());
        return userServiceResponseDTO;
    }


    public UserServiceResponseDTO signUpUser(UserDto request) {
        userDto userDto = com.iconsult.zeenebel.userservice.userDto.newBuilder()
                .setCnic(request.getCnic())
                .setPassword(request.getPassword()).
                setEmail(request.getEmail()).
                setDOB(request.getDOB())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setPhoneNumber(request.getPhoneNumber())
                .build();
        userResponse response = this.userStub.userSignUp(userDto);
        UserServiceResponseDTO userServiceResponseDTO = new UserServiceResponseDTO();
        userServiceResponseDTO.setStatus(String.valueOf(response.getStat()));
        userServiceResponseDTO.setDescription(response.getDescription());

        return userServiceResponseDTO;
    }

}
