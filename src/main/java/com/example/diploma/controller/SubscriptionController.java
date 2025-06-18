package com.example.diploma.controller;

import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.service.interfaces.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final UserService userService;

    @PostMapping("/subscribe/{fundraiserId}")
    public ResponseEntity<?> subscribeToFundraiser(@PathVariable Long fundraiserId, Principal principal) {
        userService.subscribe(principal.getName(), fundraiserId);
        return ResponseEntity.ok("Subscribed successfully");
    }

    @PostMapping("/unsubscribe/{fundraiserId}")
    public ResponseEntity<?> unsubscribeFromFundraiser(@PathVariable Long fundraiserId, Principal principal) {
        userService.unsubscribe(principal.getName(), fundraiserId);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

    @GetMapping
    public ResponseEntity<List<Fundraiser>> getUserSubscriptions(Principal principal) {
        List<Fundraiser> subscriptions = userService.getUserSubscriptions(principal.getName());
        return ResponseEntity.ok(subscriptions);
    }
}
