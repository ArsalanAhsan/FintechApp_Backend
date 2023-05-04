package com.iconsult.repository;

import com.iconsult.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    public List<Complaint> findAllByUserId(int userId);
    public Optional<Complaint> findByIdAndUserId(Long id, int userId);

}
