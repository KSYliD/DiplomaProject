package com.example.diploma.repository.admin;

import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.model.fundraiser.FundraiserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FundraiserAdminRepository extends JpaRepository<Fundraiser, Long> {
    Page<Fundraiser> findAllByStatus(FundraiserStatus status, Pageable pageable);
}

