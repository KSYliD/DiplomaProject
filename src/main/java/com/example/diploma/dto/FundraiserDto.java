package com.example.diploma.dto;

import com.example.diploma.model.fundraiser.FundraiserCategory;
import com.example.diploma.model.fundraiser.FundraiserStatus;
import com.example.diploma.model.fundraiser.Report;
import com.example.diploma.model.fundraiser.UrgencyLevel;
import com.example.diploma.model.user.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FundraiserDto {
    private long id;
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
    private LocalDateTime createdAt;
    private Report report;
    private UserDto owner;
    private List<User> subscribers;

    private boolean hasReports;
}