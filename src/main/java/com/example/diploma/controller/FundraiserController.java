package com.example.diploma.controller;

import com.example.diploma.dto.FundraiserDto;
import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.service.interfaces.fundraiser.FundraiserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/fundraisers")
@RequiredArgsConstructor
public class FundraiserController {

    private final FundraiserService fundraiserService;

    @GetMapping("/filter")
    public List<Fundraiser> getFilteredFundraisers(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean volunteer
    ) {
        return fundraiserService.getFilteredFundraisers(status, category, volunteer);
    }

    @PostMapping
    public ResponseEntity<FundraiserDto> createFundraiser(@RequestBody String jarRef,
                                                          Authentication authentication) {
        return ResponseEntity.ok(fundraiserService.create(jarRef, authentication));
    }

    @GetMapping
    public ResponseEntity<List<FundraiserDto>> getAllFundraisers() {
        return ResponseEntity.ok(fundraiserService.findAll());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FundraiserDto>> getPendingFundraisers() {
        return ResponseEntity.ok(fundraiserService.findPending());
    }

    @GetMapping("/approved")
    public ResponseEntity<List<FundraiserDto>> getApprovedFundraisers() {
        return ResponseEntity.ok(fundraiserService.findApproved());
    }

    @GetMapping("/closed")
    public ResponseEntity<List<FundraiserDto>> getClosedFundraisers() {
        return ResponseEntity.ok(fundraiserService.findClosed());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundraiserDto> getFundraiser(@PathVariable Long id) {
        return ResponseEntity.ok(fundraiserService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FundraiserDto> updateFundraiser(@PathVariable Long id, @RequestBody FundraiserDto dto) {
        return ResponseEntity.ok(fundraiserService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFundraiser(@PathVariable Long id) {
        fundraiserService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/urgent")
    public ResponseEntity<List<FundraiserDto>> getUrgentFundraisers() {
        return ResponseEntity.ok(fundraiserService.findUrgent());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FundraiserDto>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(fundraiserService.findByUser(userId));
    }
    @PostMapping("/{id}/request-publish")
    public ResponseEntity<?> requestPublish(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        boolean updated = fundraiserService.requestPublish(id, userDetails);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Нема прав для публікації або збір не знайдено");
        }
    }
}
