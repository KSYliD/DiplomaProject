package com.example.diploma.dto;

import com.example.diploma.model.fundraiser.FundraiserCategory;
import com.example.diploma.model.fundraiser.FundraiserStatus;
import com.example.diploma.model.fundraiser.UrgencyLevel;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FundraiserDto {
    private Long id;
    private String title;
    private String avatar;
    private String description;
    private String URL;
    private long targetAmount;
    private long currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private UrgencyLevel urgencyLevel;
    private FundraiserStatus status;
    private FundraiserCategory fundraiserCategory;
    private boolean fromVolunteer;
    private Long userId;
    private LocalDateTime createdAt;

}