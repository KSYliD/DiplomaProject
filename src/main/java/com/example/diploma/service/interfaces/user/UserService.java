package com.example.diploma.service.interfaces.user;

import com.example.diploma.dto.LoginRequest;
import com.example.diploma.dto.UserDto;
import com.example.diploma.model.user.User;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto updateUser(Long id, UserDto dto);
    void deleteUser(Long id);
}
