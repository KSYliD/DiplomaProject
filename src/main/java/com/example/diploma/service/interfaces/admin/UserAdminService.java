package com.example.diploma.service.interfaces.admin;

import com.example.diploma.dto.user.UserAdminDTO;
import com.example.diploma.model.user.UserStatus;

import java.util.List;

public interface UserAdminService {
    List<UserAdminDTO> getUsers(int page, int size, String role, String status);
    void updateUserRole(Long userId, List<String> roleNames);
    void updateUserStatus(Long userId, UserStatus status);
    void deleteUser(Long userId);
}
