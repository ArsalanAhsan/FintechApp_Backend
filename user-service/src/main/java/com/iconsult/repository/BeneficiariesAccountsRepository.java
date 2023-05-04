package com.iconsult.repository;

import com.iconsult.entities.BeneficiariesAccounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BeneficiariesAccountsRepository extends JpaRepository<BeneficiariesAccounts,Integer> {

public Optional<List<BeneficiariesAccounts>> findAllByUserId(String userId);
public Boolean existsByUserId(String userId);

@Query("SELECT  u from  BeneficiariesAccounts  u where u.accountNum=?1 and u.userId=?2")
 Optional<BeneficiariesAccounts> existsByAccountNumAndUserId(String acc, String userId);
}

