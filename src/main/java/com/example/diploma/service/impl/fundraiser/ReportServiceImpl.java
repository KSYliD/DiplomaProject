package com.example.diploma.service.impl.fundraiser;

import com.example.diploma.repository.ReportRepository;
import com.example.diploma.repository.FundraiserRepository;
import com.example.diploma.service.interfaces.fundraiser.ReportService;
import com.example.diploma.dto.ReportDto;
import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.model.fundraiser.Report;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final FundraiserRepository fundraiserRepository;

    @Override
    public ReportDto submitReport(Long fundraiserId, ReportDto dto) {
        Fundraiser fundraiser = fundraiserRepository.findById(fundraiserId)
                .orElseThrow(() -> new RuntimeException("Fundraiser not found"));

        Report report = new Report();
        report.setContent(dto.getContent());
        report.setCreatedAt(LocalDateTime.now());
        report.setFundraiser(fundraiser);

        Report saved = reportRepository.save(report);

        dto.setId(saved.getId());
        dto.setFundraiserId(fundraiserId);
        dto.setCreatedAt(saved.getCreatedAt());

        return dto;
    }

    @Override
    public ReportDto getReportByFundraiser(Long fundraiserId) {
        Report report = reportRepository.findByFundraiserId(fundraiserId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setContent(report.getContent());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setFundraiserId(fundraiserId);
        return dto;
    }
}