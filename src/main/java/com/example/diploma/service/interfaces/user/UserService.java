package com.example.diploma.service.interfaces.user;

import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.user.UpdateUserRequest;
import com.example.diploma.dto.user.UserResponse;
import com.example.diploma.model.fundraiser.Fundraiser;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserDto dto);
    void deleteUser(Long id);
    void subscribe(String email, Long fundraiserId);
    void unsubscribe(String email, Long fundraiserId);
    List<Fundraiser> getUserSubscriptions(String email);
    UserResponse updateCurrentUser(UpdateUserRequest request, UserDetails userDetails);
    void deleteCurrentUser(UserDetails userDetails);
}
