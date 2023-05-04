package com.iconsult.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    int accountId;
    @NonNull
    String accountNum;
    @NonNull
    String cnic;
    @NonNull
    String bankName;
    @NonNull
    String branchId;
    @NonNull
    String accType;


}
