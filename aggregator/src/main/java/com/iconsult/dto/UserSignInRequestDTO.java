package com.iconsult.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignInRequestDTO {
    int cnic;
    String pass;
}
