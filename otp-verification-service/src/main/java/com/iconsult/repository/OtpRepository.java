package com.iconsult.repository;

import com.iconsult.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Long> {

    public Otp findTopByMobileNumberOrderByTimestampDesc(String mobileNumber);

}