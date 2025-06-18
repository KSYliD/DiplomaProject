package com.example.diploma.service.impl.fundraiser;

import com.example.diploma.mapper.ReportMapper;
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
    private final ReportMapper reportMapper;

    @Override
    public ReportDto createReport(ReportDto dto) {
        Fundraiser fundraiser = fundraiserRepository.findById(dto.getFundraiserId())
                .orElseThrow(() -> new RuntimeException("Фандрейзер не знайдено"));

        // Якщо звіт вже існує, можна оновити, або кинути помилку
        if (reportRepository.findByFundraiserId(dto.getFundraiserId()).isPresent()) {
            throw new RuntimeException("Звіт для цього фандрейзера вже існує");
        }

        Report report = new Report();
        report.setFundraiser(fundraiser);
        report.setContent(dto.getContent());
        report.setFileUrl(dto.getFileUrl());
        report.setCreatedAt(LocalDateTime.now());

        Report saved = reportRepository.save(report);

        return reportMapper.reportToDto(saved);
    }

    @Override
    public ReportDto getReportByFundraiserId(Long fundraiserId) {
        Report report = reportRepository.findByFundraiserId(fundraiserId)
                .orElseThrow(() -> new RuntimeException("Звіт не знайдено"));
        return reportMapper.reportToDto(report);
    }

    @Override
    public ReportDto updateReport(ReportDto dto) {
        Report report = reportRepository.findByFundraiserId(dto.getFundraiserId())
                .orElseThrow(() -> new RuntimeException("Звіт не знайдено"));

        report.setContent(dto.getContent());
        report.setFileUrl(dto.getFileUrl());
        Report updated = reportRepository.save(report);
        return reportMapper.reportToDto(updated);
    }

    @Override
    public void deleteReport(Long fundraiserId) {
        Report report = reportRepository.findByFundraiserId(fundraiserId)
                .orElseThrow(() -> new RuntimeException("Звіт не знайдено"));
        reportRepository.delete(report);
    }
}
