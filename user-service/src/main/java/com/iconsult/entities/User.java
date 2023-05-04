package com.iconsult.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    int userId;
    @Column
    @NonNull

    String firstName;
    @Column
    @NonNull

    String lastName;
    @Column
    @NonNull

    String DOB;
    @Column
    String phoneNumber;
    @Column
    @NonNull
    String cnic;
    @Column
    @NonNull

    String email;
    @Column
    @NonNull

    String password;
}
