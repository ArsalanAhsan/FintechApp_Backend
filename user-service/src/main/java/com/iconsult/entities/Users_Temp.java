package com.iconsult.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="user_temp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users_Temp {
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    int userId;
    @Column
    @NotBlank
    String firstName ;
    @Column
    @NotBlank

    String lastName ;
    @Column
    @NotBlank
    String DOB ;
    @Column
    @NotBlank

    String  phoneNumber;
    @Column
    @NotBlank

    String cnic ;
    @Column @NotBlank

    @Email(message = "wrong pattern")
    String email;
    @Column
    String password ;
}
