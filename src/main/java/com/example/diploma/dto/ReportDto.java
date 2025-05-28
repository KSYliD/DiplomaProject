package com.example.diploma.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private Long id;
    private Long fundraiserId;
    private String content;
    private String fileUrl;
    private LocalDateTime createdAt;

}