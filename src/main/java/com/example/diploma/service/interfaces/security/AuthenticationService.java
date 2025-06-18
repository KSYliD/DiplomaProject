package com.example.diploma.service.interfaces.security;


import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.auth.AuthenticationRequest;
import com.example.diploma.dto.auth.AuthenticationResponse;
import com.example.diploma.dto.auth.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse auth(AuthenticationRequest request);
    UserDto getCurrentUser(UserDetails userDetails);
}
