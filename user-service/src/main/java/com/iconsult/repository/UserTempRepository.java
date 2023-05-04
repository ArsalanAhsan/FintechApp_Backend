package com.iconsult.repository;

import com.iconsult.entities.User;
import com.iconsult.entities.Users_Temp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTempRepository extends JpaRepository<Users_Temp,Integer> {

public Optional<User> findByCnic(String cnic);
public Optional<User> findByEmail(String emailId);
public Optional<User> findByPhoneNumber(String phoneNUm);
public Boolean existsByCnic(String cnic);
}
