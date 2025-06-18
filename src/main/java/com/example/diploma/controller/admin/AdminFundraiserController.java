package com.example.diploma.controller.admin;

import com.example.diploma.dto.FundraiserAdminDTO;
import com.example.diploma.dto.UpdateFundraiserStatusRequest;
import com.example.diploma.model.fundraiser.FundraiserStatus;
import com.example.diploma.service.interfaces.admin.FundraiserAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/admin/fundraisers")
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminFundraiserController {

    private final FundraiserAdminService fundraiserAdminService;

    @GetMapping
    public ResponseEntity<List<FundraiserAdminDTO>> getFundraisers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) FundraiserStatus status) {
        List<FundraiserAdminDTO> fundraisers = fundraiserAdminService.getFundraisers(page, size, status);
        return ResponseEntity.ok(fundraisers);
    }

    @PutMapping("/{fundraiserId}/status")
    public ResponseEntity<Void> updateFundraiserStatus(
            @PathVariable Long fundraiserId,
            @RequestBody UpdateFundraiserStatusRequest request) {
        FundraiserStatus status = request.getStatus();
        fundraiserAdminService.updateFundraiserStatus(fundraiserId, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{fundraiserId}")
    public ResponseEntity<Void> deleteFundraiser(@PathVariable Long fundraiserId) {
        log.info("log delete id: " + fundraiserId);
        fundraiserAdminService.deleteFundraiser(fundraiserId);
        return ResponseEntity.noContent().build();
    }

}
