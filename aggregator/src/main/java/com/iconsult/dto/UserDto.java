package com.iconsult.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    String firstName ;
    String lastName ;
    String DOB ;
    String  phoneNumber ;
    String cnic ;
    String email ;
    String password;
}
