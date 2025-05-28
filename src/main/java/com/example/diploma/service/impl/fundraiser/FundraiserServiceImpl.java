package com.example.diploma.service.impl.fundraiser;

import com.example.diploma.dto.FundraiserDto;
import com.example.diploma.dto.UserDto;
import com.example.diploma.mapper.FundraiserMapper;
import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.model.fundraiser.FundraiserCategory;
import com.example.diploma.model.fundraiser.FundraiserStatus;
import com.example.diploma.model.fundraiser.UrgencyLevel;
import com.example.diploma.repository.FundraiserRepository;
import com.example.diploma.service.interfaces.fundraiser.FundraiserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class FundraiserServiceImpl implements FundraiserService {

    private final FundraiserRepository fundraiserRepository;
    private final MonoJarServiceImpl monoJarService;
    private final FundraiserMapper fundraiserMapper;


    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void updateFundraiserAmounts() {
        List<Fundraiser> fundraisers = fundraiserRepository.findAll();
        for (Fundraiser fundraiser : fundraisers) {
            try {
                String jarId = extractJarIdFromUrl(fundraiser.getURL());
                Fundraiser updated = monoJarService.sendRequest(jarId, "pc");
                if(updated.getCurrentAmount() >= updated.getTargetAmount()){
                    fundraiser.setStatus(FundraiserStatus.CLOSED);
                }
                fundraiser.setCurrentAmount(updated.getCurrentAmount());
                fundraiser.setTargetAmount(updated.getTargetAmount());
                fundraiserRepository.save(fundraiser);
            } catch (Exception e) {
                log.error("Error updating fundraiser with id {}: {}", fundraiser.getId(), e.getMessage());
            }
        }
        log.info("Fundraisers updated successfully");
    }

    @Override
    public List<Fundraiser> getFilteredFundraisers(String status, String category, Boolean volunteer) {
        List<Fundraiser> all = fundraiserRepository.findAll();

        return all.stream()
                .filter(f -> status == null || f.getStatus().name().equalsIgnoreCase(status))
                .filter(f -> category == null || f.getFundraiserCategory() == FundraiserCategory.valueOf(category.toUpperCase()))
                .filter(f -> volunteer == null || f.isFromVolunteer() == volunteer)
                .collect(Collectors.toList());
    }

    @Override
    public FundraiserDto create(String jarRef, UserDto userDto) {
        String jarReference = extractMonoJarId(jarRef);
        Fundraiser fundraiser = monoJarService.sendRequest(jarReference, "pc");
        fundraiser.setStatus(FundraiserStatus.PENDING);
        fundraiser.setCreatedAt(LocalDateTime.now());
        fundraiser.setURL(extractUrlFromJson(jarRef));
        Fundraiser saved = fundraiserRepository.save(fundraiser);
        fundraiser.setId(saved.getId());
        return fundraiserMapper.fundraiserToDto(fundraiser);
    }

    @Override
    public FundraiserDto findById(Long id) {
        Fundraiser fundraiser = fundraiserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fundraiser not found"));
        return fundraiserMapper.fundraiserToDto(fundraiser);
    }

    @Override
    public List<FundraiserDto> findAll() {
        return fundraiserRepository.findAll().stream()
                .map(fundraiserMapper::fundraiserToDto)
                .toList();
    }

    @Override
    public List<FundraiserDto> findPending() {
        return fundraiserRepository.findByStatus(FundraiserStatus.PENDING).stream()
                .map(fundraiserMapper::fundraiserToDto)
                .toList();
    }

    @Override
    public List<FundraiserDto> findApproved() {
        return fundraiserRepository.findByStatus(FundraiserStatus.APPROVED).stream()
                .map(fundraiserMapper::fundraiserToDto)
                .toList();
    }

    @Override
    public List<FundraiserDto> findClosed() {
        return fundraiserRepository.findByStatus(FundraiserStatus.CLOSED).stream()
                .map(fundraiserMapper::fundraiserToDto)
                .toList();
    }

    @Override
    public List<FundraiserDto> findUrgent() {
        return fundraiserRepository.findByUrgencyLevel(UrgencyLevel.HIGH).stream()
                .map(fundraiserMapper::fundraiserToDto)
                .toList();
    }

    @Override
    public List<FundraiserDto> findByUser(Long userId) {
        return fundraiserRepository.findByUserId(userId).stream()
                .map(fundraiserMapper::fundraiserToDto)
                .toList();
    }

    @Override
    public FundraiserDto update(Long id, FundraiserDto dto) {
        Fundraiser fundraiser = fundraiserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fundraiser not found"));
        fundraiser.setTitle(dto.getTitle());
        fundraiser.setDescription(dto.getDescription());
        fundraiser.setEndDate(dto.getEndDate());
        fundraiser.setUrgencyLevel(dto.getUrgencyLevel());

        return fundraiserMapper.fundraiserToDto(fundraiserRepository.save(fundraiser));
    }

    @Override
    public FundraiserDto approveFundraiser(Long id) {
        Fundraiser fundraiser = fundraiserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fundraiser not found with id: " + id));
        fundraiser.setStatus(FundraiserStatus.APPROVED);
        fundraiserRepository.save(fundraiser);
        return fundraiserMapper.fundraiserToDto(fundraiser);
    }

    @Override
    public void delete(Long id) {
        fundraiserRepository.deleteById(id);
    }

    private String extractMonoJarId(String inputJson) {
        int urlStart = inputJson.indexOf("\"url\":\"") + 7;
        int urlEnd = inputJson.indexOf("\"", urlStart);
        String url = inputJson.substring(urlStart, urlEnd);

        if (url.contains("/jar/")) {
            return url.substring(url.lastIndexOf("/jar/") + 5);
        }
        throw new IllegalArgumentException("URL doesn't contain ID");
    }

    private String extractUrlFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(json);
            return rootNode.get("url").asText();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("URL doesn't exist");
        }
    }

    private String extractJarIdFromUrl(String url) {
        if (url.contains("/jar/")) {
            return url.substring(url.lastIndexOf("/jar/") + 5);
        }
        throw new IllegalArgumentException("URL doesn't contain jar ID");
    }
}
