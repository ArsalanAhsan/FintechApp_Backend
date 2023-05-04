package com.iconsult.entities;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "beneficaries_accounts")
@Builder
public class BeneficiariesAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int beneficiaryId;
@NonNull()
    String accountNum;

    @Column
    @NonNull
    String userId;

    @Column
    @NonNull
    String bank;

    @Column
    String alias;
}




