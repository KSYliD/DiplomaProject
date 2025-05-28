package com.example.diploma.dto;

import com.example.diploma.model.fundraiser.Fundraiser;
import com.example.diploma.model.user.Role;
import com.example.diploma.model.user.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private List<Fundraiser> fundraisers;
    private List<Fundraiser> subscriptions;
}