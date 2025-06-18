package com.example.diploma.service.impl.admin;

import com.example.diploma.dto.user.UserAdminDTO;
import com.example.diploma.model.user.Role;
import com.example.diploma.model.user.User;
import com.example.diploma.model.user.UserStatus;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.repository.admin.UserAdminRepository;
import com.example.diploma.service.interfaces.admin.UserAdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {

    private final UserAdminRepository userAdminRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<UserAdminDTO> getUsers(int page, int size, String role, String status) {
        List<User> users;

        if (role != null && status != null) {
            users = userAdminRepository.findByRoleAndStatusExcludingAdmin(role, UserStatus.valueOf(status));
        } else if (role != null) {
            users = userAdminRepository.findByRoleExcludingAdmin(role);
        } else if (status != null) {
            users = userAdminRepository.findByStatusExcludingAdmin(UserStatus.valueOf(status));
        } else {
            users = userAdminRepository.findAllExceptAdmins();
        }

        return users.stream()
                .map(UserAdminDTO::fromEntity)
                .collect(Collectors.toList());
    }



    @Transactional
    public void updateUserRole(Long userId, List<String> roleNames) {
        Set<Role> roles = roleRepository.findByNameIn(roleNames); // шукаємо ролі по імені
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(roles);
        userRepository.save(user);
    }


    @Transactional
    public void updateUserStatus(Long userId, UserStatus status) {
        User user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        userAdminRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userAdminRepository.deleteById(userId);
    }


}
