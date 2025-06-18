package com.example.diploma.dto;

import com.example.diploma.model.fundraiser.FundraiserStatus;

public class UpdateFundraiserStatusRequest {
    private FundraiserStatus status;

    public FundraiserStatus getStatus() {
        return status;
    }

    public void setStatus(FundraiserStatus status) {
        this.status = status;
    }
}

