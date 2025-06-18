package com.example.diploma.controller;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.service.interfaces.fundraiser.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fundraisers/{fundraiserId}/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportDto> submitReport(@PathVariable Long fundraiserId,
                                                  @RequestBody ReportDto reportDto) {
        reportDto.setFundraiserId(fundraiserId);
        ReportDto createdReport = reportService.createReport(reportDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    @GetMapping
    public ResponseEntity<ReportDto> getReport(@PathVariable Long fundraiserId) {
        ReportDto report = reportService.getReportByFundraiserId(fundraiserId);
        return ResponseEntity.ok(report);
    }

    @PutMapping
    public ResponseEntity<ReportDto> updateReport(@PathVariable Long fundraiserId,
                                                  @RequestBody ReportDto reportDto) {
        reportDto.setFundraiserId(fundraiserId);
        ReportDto updated = reportService.updateReport(reportDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteReport(@PathVariable Long fundraiserId) {
        reportService.deleteReport(fundraiserId);
        return ResponseEntity.noContent().build();
    }
}
