package com.iconsult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class addBeneficiaryRequestDto {

    @NotBlank(message = "must be filled")
    String accountNum;
    String alias;
    @NotBlank(message = "must be filled")
    String bank;
}
