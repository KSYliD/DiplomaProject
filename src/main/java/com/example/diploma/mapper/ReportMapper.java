package com.example.diploma.mapper;

import com.example.diploma.dto.ReportDto;
import com.example.diploma.model.fundraiser.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    Report dtoToReport(ReportDto reportDto);
    ReportDto reportToDto(Report report);
}
