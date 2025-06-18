package com.example.diploma.controller.admin;

import com.example.diploma.dto.user.UserAdminDTO;
import com.example.diploma.model.user.UserStatus;
import com.example.diploma.service.interfaces.admin.UserAdminService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminUserController {

    private final UserAdminService userAdminService;

    public AdminUserController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    // Отримати список користувачів з пагінацією і фільтрами
    @GetMapping
    public ResponseEntity<List<UserAdminDTO>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status) {
        List<UserAdminDTO> users = userAdminService.getUsers(page, size, role, status);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<Void> updateUserRole(
            @PathVariable Long userId,
            @RequestBody List<String> role) {  // Приймаємо список імен ролей
        log.info(role.toString());
        userAdminService.updateUserRole(userId, role);
        return ResponseEntity.ok().build();
    }


    // Заблокувати/розблокувати користувача
    @PutMapping("/{userId}/status")
    public ResponseEntity<Void> updateUserStatus(
            @PathVariable Long userId,
            @RequestParam UserStatus status) {
        userAdminService.updateUserStatus(userId, status);
        return ResponseEntity.ok().build();
    }

    // Видалити користувача
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userAdminService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
