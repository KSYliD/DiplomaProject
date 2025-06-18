package com.example.diploma.service.impl.admin;

import com.example.diploma.dto.FundraiserAdminDTO;
import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.model.fundraiser.FundraiserStatus;
import com.example.diploma.model.user.User;
import com.example.diploma.repository.admin.FundraiserAdminRepository;
import com.example.diploma.service.interfaces.admin.FundraiserAdminService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundraiserAdminServiceImpl implements FundraiserAdminService {

    private final FundraiserAdminRepository fundraiserAdminRepository;

    public List<FundraiserAdminDTO> getFundraisers(int page, int size, FundraiserStatus status) {
        Pageable pageable = PageRequest.of(page, size);
        if (status == null) {
            status = FundraiserStatus.PENDING;
        }
        return fundraiserAdminRepository
                .findAllByStatus(status, pageable)
                .stream()
                .map(FundraiserAdminDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateFundraiserStatus(Long fundraiserId, FundraiserStatus status) {
        Fundraiser fundraiser = fundraiserAdminRepository.findById(fundraiserId)
                .orElseThrow(() -> new RuntimeException("Fundraiser not found"));
        fundraiser.setStatus(status);
        fundraiserAdminRepository.save(fundraiser);
    }

    @Transactional
    public void deleteFundraiser(Long fundraiserId) {
        Fundraiser fundraiser = fundraiserAdminRepository.findById(fundraiserId)
                .orElseThrow(() -> new EntityNotFoundException("Fundraiser not found"));

        for (User subscriber : fundraiser.getSubscribers()) {
            subscriber.getSubscriptions().remove(fundraiser);
        }
        fundraiser.getSubscribers().clear();

        fundraiserAdminRepository.delete(fundraiser);
    }

}
