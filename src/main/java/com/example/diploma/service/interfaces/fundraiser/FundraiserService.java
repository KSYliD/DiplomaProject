package com.example.diploma.service.interfaces.fundraiser;

import com.example.diploma.dto.FundraiserDto;
import com.example.diploma.model.fundraiser.Fundraiser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface FundraiserService {
    List<Fundraiser> getFilteredFundraisers(String status, String category, Boolean volunteer);
    FundraiserDto create(String jarRef, Authentication authentication);
    FundraiserDto findById(Long id);
    List<FundraiserDto> findAll();
    List<FundraiserDto> findPending();
    List<FundraiserDto> findApproved();
    List<FundraiserDto> findClosed();
    List<FundraiserDto> findUrgent();
    List<FundraiserDto> findByUser(Long userId);
    FundraiserDto update(Long id, FundraiserDto dto);
    void delete(Long id);
    boolean requestPublish(Long fundraiserId, UserDetails userDetails);
}
