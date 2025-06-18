package com.example.diploma.service.interfaces.admin;

import com.example.diploma.dto.FundraiserAdminDTO;
import com.example.diploma.model.fundraiser.FundraiserStatus;

import java.util.List;

public interface FundraiserAdminService {
    List<FundraiserAdminDTO> getFundraisers(int page, int size, FundraiserStatus status);
    void updateFundraiserStatus(Long fundraiserId, FundraiserStatus status);
    void deleteFundraiser(Long fundraiserId);
}
