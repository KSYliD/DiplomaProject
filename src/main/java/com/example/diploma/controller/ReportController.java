package com.example.diploma.controller;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.service.interfaces.fundraiser.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportDto> submitReport(@RequestBody ReportDto dto) {
        return ResponseEntity.ok(reportService.submitReport( dto.getId(), dto));
    }

    @GetMapping("/{fundraiserId}")
    public ResponseEntity<ReportDto> getReport(@PathVariable Long fundraiserId) {
        return ResponseEntity.ok(reportService.getReportByFundraiser(fundraiserId));
    }
}

