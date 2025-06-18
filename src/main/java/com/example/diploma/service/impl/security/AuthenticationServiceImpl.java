package com.example.diploma.service.impl.security;


import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.auth.AuthenticationRequest;
import com.example.diploma.dto.auth.AuthenticationResponse;
import com.example.diploma.dto.auth.RegisterRequest;
import com.example.diploma.exception.FailedToAccessException;
import com.example.diploma.exception.UnauthorizedException;
import com.example.diploma.mapper.UserMapper;
import com.example.diploma.model.user.Role;
import com.example.diploma.model.user.User;
import com.example.diploma.model.user.UserStatus;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.interfaces.security.AuthenticationService;
import com.example.diploma.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtils validationUtils;
    private final RoleRepository roleRepository;


    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        validationUtils.validate(request);
        User user = userMapper.registerRequestToUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("User"));
        user.setRole(roles);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse auth(AuthenticationRequest request) {
        validationUtils.validate(request);
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new FailedToAccessException("User not found with email: " + request.getEmail()));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new FailedToAccessException("Password does not match");
        }
        new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userDto(userMapper.userToUserDto(user))
                .build();
    }

    public UserDto getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new UnauthorizedException("Користувач не автентифікований");
        }

        return userRepository.findByEmail(userDetails.getUsername())
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new UsernameNotFoundException("Користувача з таким email не знайдено"));
    }

}
