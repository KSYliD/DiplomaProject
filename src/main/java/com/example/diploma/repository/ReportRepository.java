package com.example.diploma.repository;

import com.example.diploma.model.fundraiser.Report;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByFundraiserId(Long fundraiserId);
}
