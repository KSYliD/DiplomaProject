package com.example.diploma.repository;

import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.model.fundraiser.FundraiserStatus;
import com.example.diploma.model.fundraiser.UrgencyLevel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface FundraiserRepository extends JpaRepository<Fundraiser, Long> {
    List<Fundraiser> findByOwnerId(Long ownerId);
    List<Fundraiser> findByStatus(FundraiserStatus status);
    List<Fundraiser> findByUrgencyLevel(UrgencyLevel urgencyLevel);
}
