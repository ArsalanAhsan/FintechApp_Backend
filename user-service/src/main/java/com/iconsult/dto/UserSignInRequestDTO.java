package com.iconsult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class UserSignInRequestDTO {
    @NotBlank(message = "must be filled")
    String cnic;
    @NotBlank(message = "must be filled")
    String pass;
}
