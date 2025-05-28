package com.example.diploma.controller;

import com.example.diploma.dto.FundraiserDto;
import com.example.diploma.dto.UserDto;
import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.service.interfaces.fundraiser.FundraiserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<FundraiserDto> createFundraiser(@RequestBody String jarRef, @AuthenticationPrincipal UserDto userDto) {
        return ResponseEntity.ok(fundraiserService.create(jarRef, userDto));
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
}
