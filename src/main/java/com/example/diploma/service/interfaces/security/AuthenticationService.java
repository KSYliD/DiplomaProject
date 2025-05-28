package com.example.diploma.service.interfaces.security;


import com.example.diploma.dto.auth.AuthenticationRequest;
import com.example.diploma.dto.auth.AuthenticationResponse;
import com.example.diploma.dto.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse auth(AuthenticationRequest request);
}
