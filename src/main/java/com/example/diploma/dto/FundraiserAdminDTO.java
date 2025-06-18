package com.example.diploma.dto;

import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.model.fundraiser.FundraiserStatus;
import lombok.Data;

@Data
public class FundraiserAdminDTO {
    private Long id;
    private String title;
    private FundraiserStatus status;
    private String createdBy; // email
    private String ownerFirstName;
    private String ownerLastName;

    public static FundraiserAdminDTO fromEntity(Fundraiser fundraiser) {
        FundraiserAdminDTO dto = new FundraiserAdminDTO();
        dto.setId(fundraiser.getId());
        dto.setTitle(fundraiser.getTitle());
        dto.setStatus(fundraiser.getStatus());
        dto.setCreatedBy(fundraiser.getOwner().getEmail());
        dto.setOwnerFirstName(fundraiser.getOwner().getFirstName());
        dto.setOwnerLastName(fundraiser.getOwner().getLastName());
        return dto;
    }
}


