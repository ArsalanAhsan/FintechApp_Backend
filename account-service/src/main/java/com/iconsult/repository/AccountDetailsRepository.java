package com.iconsult.repository;

import com.iconsult.entities.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails,Integer> {

public List<AccountDetails> findAllByCnic(String cnic);
public Boolean existsByCnic(String cnic);

public AccountDetails findByAccountNum(String accountNum);
public Boolean existsByAccountNum(String acc);
}
