package com.iconsult.service.Impl;

import com.iconsult.entity.Complaint;
import com.iconsult.response.ApiResponse;
import com.iconsult.service.ComplaintService;
import com.iconsult.zeenebel.Constants.ProductType;
import com.iconsult.zeenebel.complaintservice.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    public ModelMapper modelMapper;

    @GrpcClient("complaint-service")
    private ComplaintServiceGrpc.ComplaintServiceBlockingStub complaintStub;
    @Override
    public ApiResponse getAllComplaint(int userId) {
        GetAllComplaintRequest getAllComplaintRequest = GetAllComplaintRequest.newBuilder().setUserId(userId).build();
        GetAllComplaintResponse response = complaintStub.getAllComplaint(getAllComplaintRequest);
        List<Complaint> complaints = response.getComplaintsList().stream().map(complaint -> modelMapper.map(complaint, Complaint.class)).collect(Collectors.toList());
        return new ApiResponse(HttpStatus.OK.value(), complaints);
    }

    @Override
    public ApiResponse addComplaint(Complaint complaint) {
        AddComplaintRequest addComplaintRequest = AddComplaintRequest.newBuilder().setDetail(complaint.getDetail()).setSubject(complaint.getSubject()).setProductType(ProductType.valueOf(complaint.getProductType().toUpperCase())).setUserId(complaint.getUserId()).build();
        GeneralResponse response = complaintStub.addComplaint(addComplaintRequest);
        Complaint complaintResponse = modelMapper.map(response,Complaint.class);
        return new ApiResponse(HttpStatus.OK.value(),complaintResponse);
    }

    @Override
    public ApiResponse getComplaintById(Long id,int userId) {
        GetComplaintRequest.Builder getComplaintRequest = GetComplaintRequest.newBuilder().setId(id).setUserId(userId);
        ComplaintResponse response = complaintStub.getComplaintById(getComplaintRequest.build());
        Complaint complaint = modelMapper.map(response.getComplaint(), Complaint.class);
        return new ApiResponse(HttpStatus.OK.value(),complaint);
    }

    @Override
    public ApiResponse updateComplaint(Complaint complaint, Long id) {
        return null;
    }

    @Override
    public ApiResponse deleteComplaint(Long id) {
        return null;
    }
}
