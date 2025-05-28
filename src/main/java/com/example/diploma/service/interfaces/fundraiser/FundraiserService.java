package com.example.diploma.service.interfaces.fundraiser;

import com.example.diploma.dto.FundraiserDto;
import com.example.diploma.dto.UserDto;
import com.example.diploma.model.fundraiser.Fundraiser;

import java.util.List;

public interface FundraiserService {
    List<Fundraiser> getFilteredFundraisers(String status, String category, Boolean volunteer);
    FundraiserDto create(String jarRef, UserDto userDto);
    FundraiserDto findById(Long id);
    List<FundraiserDto> findAll();
    List<FundraiserDto> findPending();
    List<FundraiserDto> findApproved();
    List<FundraiserDto> findClosed();
    List<FundraiserDto> findUrgent();
    List<FundraiserDto> findByUser(Long userId);
    FundraiserDto update(Long id, FundraiserDto dto);
    FundraiserDto approveFundraiser(Long id);
    void delete(Long id);
}
