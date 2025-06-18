package com.example.diploma.service.interfaces.fundraiser;

import com.example.diploma.dto.ReportDto;

public interface ReportService {
    ReportDto createReport(ReportDto dto);
    ReportDto getReportByFundraiserId(Long fundraiserId);
    ReportDto updateReport(ReportDto dto);
    void deleteReport(Long fundraiserId);
}
