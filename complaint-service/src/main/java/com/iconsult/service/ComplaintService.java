package com.iconsult.service;


import com.iconsult.entity.Complaint;
import com.iconsult.response.ApiResponse;

public interface ComplaintService {


    ApiResponse getAllComplaint(int userId);

    ApiResponse addComplaint(Complaint complaint);

    ApiResponse getComplaintById(Long id,int userId);

    ApiResponse updateComplaint(Complaint complaint, Long id);

    ApiResponse deleteComplaint(Long id);
}
