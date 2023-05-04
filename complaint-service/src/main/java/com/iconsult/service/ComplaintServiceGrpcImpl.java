package com.iconsult.service;

import com.iconsult.entity.Complaint;
import com.iconsult.repository.ComplaintRepository;
import com.iconsult.zeenebel.Constants.ProductType;
import com.iconsult.zeenebel.Constants.statusResponse;
import com.iconsult.zeenebel.complaintservice.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@GrpcService
public class ComplaintServiceGrpcImpl extends ComplaintServiceGrpc.ComplaintServiceImplBase {


    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addComplaint(AddComplaintRequest request, StreamObserver<GeneralResponse> responseObserver) {
        Complaint complaint = modelMapper.map(request, Complaint.class);
        Complaint save = complaintRepository.save(complaint);
        GeneralResponse.Builder builder = GeneralResponse.newBuilder();
        responseObserver.onNext( builder.setStat(statusResponse.Record_Added_Successfully).setDescription("Successfully Added Complaint").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getAllComplaint(GetAllComplaintRequest request, StreamObserver<GetAllComplaintResponse> responseObserver) {
        List<Complaint> complaints = complaintRepository.findAllByUserId(request.getUserId());
        GetAllComplaintResponse.Builder builder = GetAllComplaintResponse.newBuilder();
        List<ComplaintDto> complaintDtoList = new ArrayList<>();
        if(complaints!=null && !complaints.isEmpty()){
           for (Complaint complain: complaints) {
                complaintDtoList.add(ComplaintDto.newBuilder()
                        .setProductType(ProductType.valueOf(complain.getProductType()))
                        .setDetail(complain.getDetail())
                        .setSubject(complain.getSubject())
                        .setId(complain.getId())
                        .build());
           }
        }
        responseObserver.onNext(builder.addAllComplaints(complaintDtoList).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getComplaintById(GetComplaintRequest request, StreamObserver<ComplaintResponse> responseObserver) {
        ComplaintResponse.Builder builder = ComplaintResponse.newBuilder();
        Optional<Complaint> complaint = complaintRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if(complaint.isPresent()){
            Complaint complaint1 = complaint.get();
            ComplaintDto complaintDto = ComplaintDto.newBuilder().setId(complaint1.getId()).setDetail(complaint1.getDetail()).setSubject(complaint1.getSubject())
                    .setProductType(ProductType.valueOf(complaint1.getProductType())).build();
            builder.setComplaint(complaintDto).build();
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

    }
}
