package com.example.diploma.service.interfaces.fundraiser;

import com.example.diploma.dto.ReportDto;

public interface ReportService {
    ReportDto submitReport(Long fundraiserId, ReportDto dto);
    ReportDto getReportByFundraiser(Long fundraiserId);
}
