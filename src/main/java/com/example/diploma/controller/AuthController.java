package com.example.diploma.controller;


import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.auth.AuthenticationRequest;
import com.example.diploma.dto.auth.AuthenticationResponse;
import com.example.diploma.dto.auth.RegisterRequest;
import com.example.diploma.service.interfaces.security.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authService;


    @PostMapping("/register")
    @SecurityRequirements()
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    @SecurityRequirements()
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.auth(request));
    }
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDto userDto) {
        return ResponseEntity.ok(userDto);
    }
}
