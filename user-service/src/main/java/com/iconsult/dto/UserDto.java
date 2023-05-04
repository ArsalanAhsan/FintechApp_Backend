package com.iconsult.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "must be filled")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "must be filled")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank(message = "must be filled")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$")
    private String dob;

    @NotBlank(message = "must be filled")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$")
    private String phoneNumber;

    @NotBlank(message = "must be filled")
    @Pattern(regexp = "^\\d{13}$")
    private String cnic;

    @NotBlank(message = "must be filled")
    @Email
    private String email;

    @NotBlank(message = "must be filled")
    @Size(min = 8,message = "min size 8 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",message = "password must be alpha numeric with at least one special character  ")
    private String password;


    // Getters and setters for each field
}
