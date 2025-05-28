package com.example.diploma.mapper;

import com.example.diploma.dto.FundraiserDto;
import com.example.diploma.model.fundraiser.Fundraiser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FundraiserMapper {
    Fundraiser dtoToFundraiser(FundraiserDto fundraiserDto);
    FundraiserDto fundraiserToDto(Fundraiser fundraiser);
}
