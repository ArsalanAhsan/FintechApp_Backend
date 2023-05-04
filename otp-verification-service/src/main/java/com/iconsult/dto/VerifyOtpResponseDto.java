package com.iconsult.dto;

import com.iconsult.zeenebel.Constants.EmailOtp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyOtpResponseDto {

    EmailOtp emailOtpResponse;
}
